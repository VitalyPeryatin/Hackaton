package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.repository.CardRepository
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.domain.ICardRepository
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import com.infinity_coder.hackatonapp.regexBankCardName
import com.infinity_coder.hackatonapp.regexDate
import com.infinity_coder.hackatonapp.regexHolderName
import kotlinx.android.synthetic.main.activity_edit_bank_card.*

class BankEditCardActivity: AppCompatActivity() {
    var errorStack = ""

    lateinit var cardRepository: ICardRepository
    lateinit var card: BankCard

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bank_card)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        cardRepository = CardRepository()
        card = TempRepository.card as BankCard

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        tv_bank_card_number.setText(card.number)
        tv_holder_name.setText("${card.name} ${card.surName}")
        tv_expiring_date.setText(card.validThru)
        findViewById<EditText>(R.id.etCompany).setText(card.company)
    }

    private fun validateFields() {

        if(!tv_bank_card_number.text.toString().matches(regexBankCardName)) errorStack += "Неправильно введён номер карты\n\n"
        if(!tv_holder_name.text.toString().matches(regexHolderName)) errorStack += "Неправильно введено имя держателя карты\n\n"
        if(!tv_expiring_date.text.toString().matches(regexDate)) errorStack += "Неправильно введён срок действия\n\n"
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
                validateFields()
                if(errorStack.isEmpty()){
                    card.number = tv_bank_card_number.text.toString()
                    card.validThru = tv_expiring_date.text.toString()
                    card.company = etCompany.text.toString()
                    card.name = tv_holder_name.text.toString()
                    Log.d("BEAC", card.number + card.validThru)
                    /*val cardMock:BankCard
                    cardMock = BankCard(tv_bank_card_number.text.toString(), tv_expiring_date.text.toString(), etCompany.text.toString(), tv_holder_name.text.toString())
                    cardRepository.insert(cardMock)*/
                    cardRepository.insert(card)
                    startActivity(Intent(this, OverviewCardActivity::class.java))
                }
                else showError(errorStack); errorStack = ""
            }
        }
        return true
    }

    private fun showError(err:String){
        val mErrorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(err)
            .show()
        mErrorDialog.setOnCancelListener{
            if (mErrorDialog != null && mErrorDialog.isShowing) mErrorDialog.cancel()
        }
    }
}