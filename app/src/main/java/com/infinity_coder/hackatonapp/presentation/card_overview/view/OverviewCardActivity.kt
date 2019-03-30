package com.infinity_coder.hackatonapp.presentation.card_overview.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import kotlinx.android.synthetic.main.activity_overview_card.*


class OverviewCardActivity: AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_card)
        setSupportActionBar(toolbarOverview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val card = TempRepository.card

        if(card is BankCard){
            tvNumber.visibility = GONE
            if(card.validThru.toString().isEmpty())
                tvDate.visibility = GONE
            else {
                tvDate.visibility = VISIBLE
                tvDate.text = card.validThru.toString()
            }
            if(card.name.isEmpty() && card.surName.isEmpty())
                tvHolderName.visibility = GONE
            else {
                tvHolderName.visibility = VISIBLE
                tvHolderName.text = "${card.name} ${card.surName}"
            }
            if(card.number.isEmpty())
                tvBankNumber.visibility = GONE
            else {
                tvBankNumber.visibility = VISIBLE
                tvBankNumber.text = card.number
            }
        }
        else if(card is FuelCard){
            tvHolderName.visibility = GONE
            if(card.validThru.toString().isEmpty())
                tvDate.visibility = GONE
            else {
                tvDate.visibility = VISIBLE
                tvDate.text = card.validThru.toString()
            }
            if(card.number.isEmpty())
                tvBankNumber.visibility = GONE
            else {
                tvBankNumber.visibility = VISIBLE
                tvBankNumber.text = card.number
            }
            if(card.subNumber.toString().isEmpty())
                tvNumber.visibility = GONE
            else {
                tvNumber.visibility = VISIBLE
                tvNumber.text = card.number
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.edit -> {
                //TODO("Make navigation")
            }

            android.R.id.home -> onBackPressed()

        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /*if(requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            Picasso.get()
                .load(File(data.getStringExtra(IMAGE_PATH_KEY)))
                .into(cardImage)
        }*/
    }
}