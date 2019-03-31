package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions.SPARSE_MODEL
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.infinity_coder.hackatonapp.*
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.presentation.edit_card.view_model.EditViewModel
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity

class EditCardActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card)
        navigateTo(ProcessingFragment())
        startActivityForResult(
            Intent(this, ScanActivity::class.java),
            SCAN_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val viewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)
        viewModel.navigatoLive.observe(this, Observer{
            if (it is BankCard)
                navigateTo(BankEditCardFragment())
            else
                navigateTo(FuelEditCardFragment())
        })

        viewModel.errorListener.observe(this, Observer{
            showToast("Bank card not recognized!")
            finish()
        })
        if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imagePath = data?.getStringExtra(IMAGE_PATH_KEY)!!
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            viewModel.imagePath = imagePath
            val bitmap = BitmapFactory.decodeFile(imagePath, options)

            if (isNetworkConnected()) {
                viewModel.runCloudTextRecognition(bitmap)
                showToast("Running cloud text recognition")
            } else {
                viewModel.runTextRecognition(bitmap)
                showToast("Running on-device text recognition")
            }
        } else {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }


}