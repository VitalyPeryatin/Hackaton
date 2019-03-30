package com.infinity_coder.hackatonapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_overview_card.*
import java.io.File

class OverviewCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_card)
        buttonGoScan.setOnClickListener {
            startActivityForResult(Intent(this, ScanActivity::class.java), SCAN_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            Picasso.get()
                .load(File(data.getStringExtra(IMAGE_PATH_KEY)))
                .into(cardImage)
        }
    }
}