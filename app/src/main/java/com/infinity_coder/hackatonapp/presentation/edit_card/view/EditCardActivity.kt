package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.infinity_coder.hackatonapp.IMAGE_PATH_KEY
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.SCAN_REQUEST_CODE
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity

class EditCardActivity: AppCompatActivity() {
    val tempRepository = TempRepository
    var cardNumber = ""
    var holderName = ""
    var bankCardNumber = ""
    var expiringDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)
        startActivityForResult(
            Intent(this, ScanActivity::class.java),
            SCAN_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE) {
            Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show()
            val imagePath = data?.getStringExtra(IMAGE_PATH_KEY)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            val bitmap = BitmapFactory.decodeFile(imagePath, options)


            if (isNetworkConnected()) {
                runCloudTextRecognition(bitmap)
                showToast("Running cloud text recognition")
            } else {
                runTextRecognition(bitmap)
                showToast("Running on-device text recognition")
            }
        } else {
            finish()
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun runTextRecognition(mSelectedImage: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
//        mTextButton.setEnabled(false)
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                //                mTextButton.setEnabled(true)
                processTextRecognitionResult(texts)
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                //                        mTextButton.setEnabled(true)
                e.printStackTrace()
            }
    }

    private fun processTextRecognitionResult(texts: FirebaseVisionText) {
        val blocks = texts.textBlocks

        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                val elements = lines[j].elements
                if (lines[j].text.replace(" ", "").matches("\\d{10}".toRegex()))
                    cardNumber = (lines[j].text)
                if (lines[j].text.matches("[A-Z]+\\s([A-Z])+".toRegex()))
                    holderName = (lines[j].text)
                if (lines[j].text.replace(" ", "")
                        .matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$".toRegex()))
                    bankCardNumber = (lines[j].text)
                for (k in elements.indices) {
                    if (elements[k].text.matches(Regex("^\\d{2}\\/\\d{2}$"))) {
                        expiringDate = (elements[k].text)
                    }
                }
            }
        }

        val intent = Intent(this, BankEditCardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun runCloudTextRecognition(mSelectedImage: Bitmap) {
//        mCloudButton.setEnabled(false)
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .cloudTextRecognizer
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                processCloudTextRecognitionResult(texts)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun processCloudTextRecognitionResult(text: FirebaseVisionText) {

        val blocks = text.textBlocks
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                if (lines[j].text.replace(" ", "").matches("\\d{10}".toRegex())) {
                    cardNumber = (lines[j].text)
                }
                if (lines[j].text.matches("[A-Z]+\\s([A-Z])+".toRegex())) {
                    holderName = (lines[j].text)
                }
                if (lines[j].text.replace(" ", "")
                        .matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$".toRegex())
                ) {
                    bankCardNumber = (lines[j].text)
                }

                //                showToast(lines.get(j).getText());
                val elements = lines[j].elements
                for (l in elements.indices) {
                    //                    CloudTextGraphic cloudDocumentTextGraphic = new CloudTextGraphic(mGraphicOverlay,
                    //                            words.get(l));
                    //                    mGraphicOverlay.add(cloudDocumentTextGraphic);
                    if (elements[l].text.matches(Regex("^\\d{2}\\/\\d{2}$"))) {
                        expiringDate = (elements[l].text)
                    }
                }
            }
            if (holderName != ""){
                tempRepository.card = BankCard(cardNumber, expiringDate, "", holderName.split(" ")[0], holderName.split(" ")[1])
            }
            else{
                tempRepository.card = FuelCard(cardNumber, expiringDate, "")
            }
        }

        TempRepository.card = BankCard(bankCardNumber)

        val intent = Intent(this, BankEditCardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }
}