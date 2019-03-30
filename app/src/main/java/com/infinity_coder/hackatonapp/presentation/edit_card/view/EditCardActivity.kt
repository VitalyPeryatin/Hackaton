package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import kotlinx.android.synthetic.main.activity_edit_card.*

class EditCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_accept, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.accept -> {
                startActivity(Intent(this, OverviewCardActivity::class.java))
            }
        }
        return true
    }
}