package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import kotlinx.android.synthetic.main.activity_edit_fuel_card.*

class FuelEditCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fuel_card)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }
}