package com.infinity_coder.hackatonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.file.Files

class MainActivity : AppCompatActivity(), RecyclerCardAdapter.Listener {
    override fun onClick(id: String) {
        startActivity(Intent(this,ScanActivity::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerCards.layoutManager = GridLayoutManager(this,2)

        recyclerCards.adapter = RecyclerCardAdapter(this, this)


        fabAdd.setOnClickListener{
            startActivity(Intent(this,ScanActivity::class.java))

        }
    }


}
