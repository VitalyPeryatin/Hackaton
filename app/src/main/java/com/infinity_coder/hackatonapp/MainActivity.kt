package com.infinity_coder.hackatonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGoScan.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,ScanActivity::class.java))

        })
    }


}
