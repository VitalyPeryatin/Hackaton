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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_overview_card.*
import java.io.File


class OverviewCardActivity: AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_card)
        setSupportActionBar(toolbarOverview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val card = TempRepository.card

        Picasso.get()
            .load(File(card.path))
            .into(imageView)

        if(card is BankCard){
            tvDateContainer.visibility = GONE
            if(card.validThru.isEmpty())
                tvDateContainer.visibility = GONE
            else {
                tvDateContainer.visibility = VISIBLE
                tvDate.text = card.validThru
            }
            if(card.name.isEmpty() && card.surName.isEmpty())
                tvHolderNameContainer.visibility = GONE
            else {
                tvHolderNameContainer.visibility = VISIBLE
                tvHolderName.text = "${card.name} ${card.surName}"
            }
            if(card.number.isEmpty())
                tvBankNumberContainer.visibility = GONE
            else {
                tvBankNumberContainer.visibility = VISIBLE
                tvBankNumber.text = card.number
            }
            if(card.company.isEmpty()){
                tvNumberContainer.visibility = GONE
            }else tvNumberContainer.visibility = VISIBLE
        }
        else if(card is FuelCard){
            tvHolderName.visibility = GONE
            if(card.validThru.isEmpty())
                tvDateContainer.visibility = GONE
            else {
                tvDateContainer.visibility = VISIBLE
                tvDate.text = card.validThru
            }
            if(card.number.isEmpty())
                tvBankNumberContainer.visibility = GONE
            else {
                tvBankNumberContainer.visibility = VISIBLE
                tvBankNumber.text = card.number
            }
            if(card.subNumber.toString().isEmpty())
                tvNumberContainer.visibility = GONE
            else {
                //Не тот контейнер. Этого элемента вообще нет
                //Нужен - создайте
                tvNumberContainer.visibility = VISIBLE
                tvNumber.text = card.subNumber
            }
            //Здесь всё правильно. Id перепутан, ну и хуй с ним, я не буду ничего менять
            //Платёжная система блять не отображается, найди этот ебучий setText() и блять поменяй id
            //правильный id - tvNumber
            //сами виноваты блять, нехуй было id путать
            if(card.company.isEmpty()){
                tvNumberContainer.visibility = GONE
            }else tvNumberContainer.visibility = VISIBLE
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