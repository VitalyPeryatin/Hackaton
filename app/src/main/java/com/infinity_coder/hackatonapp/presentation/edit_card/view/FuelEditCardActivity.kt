package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import kotlinx.android.synthetic.main.activity_edit_fuel_card.*

class FuelEditCardActivity: AppCompatActivity() {

    lateinit var card: FuelCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fuel_card)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        card = TempRepository.card as FuelCard

        tv_bank_card_number.setText(card.number)
        tv_card_number.setText(card.subNumber)
        tv_expiring_date.setText(card.validThru.toString())

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_accept, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
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