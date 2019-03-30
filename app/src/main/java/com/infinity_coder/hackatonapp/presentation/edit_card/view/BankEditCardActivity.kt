package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.repository.CardRepository
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.domain.ICardRepository
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import kotlinx.android.synthetic.main.activity_edit_bank_card.*

class BankEditCardActivity: AppCompatActivity() {
    lateinit var repository: ICardRepository
    lateinit var card: BankCard

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bank_card)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        repository = CardRepository()
        card = TempRepository.card as BankCard

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        tv_bank_card_number.setText(card.number)
        tv_holder_name.setText("${card.name} ${card.surName}")
        tv_expiring_date.setText(card.validThru.toString())
        //repository.insert(card as BankCard)
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
                repository.insert(card)
                startActivity(Intent(this, OverviewCardActivity::class.java))
            }
        }
        return true
    }
}