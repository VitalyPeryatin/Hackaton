package com.infinity_coder.hackatonapp.presentation.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.presentation.main.adapter.RecyclerCardAdapter
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerCardAdapter.Listener {
    override fun onClick(number: String) {
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
