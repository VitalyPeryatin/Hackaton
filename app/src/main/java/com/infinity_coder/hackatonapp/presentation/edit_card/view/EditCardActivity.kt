package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.IMAGE_PATH_KEY
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.SCAN_REQUEST_CODE
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_card.*
import kotlinx.android.synthetic.main.activity_overview_card.*
import java.io.File

class EditCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_card)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        startActivityForResult(Intent(this, ScanActivity::class.java),
            SCAN_REQUEST_CODE
        )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
        }
        else{
            finish()
        }
    }
}