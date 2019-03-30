package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import com.infinity_coder.hackatonapp.regexBankCardName
import com.infinity_coder.hackatonapp.regexDate
import com.infinity_coder.hackatonapp.regexHolderName
import kotlinx.android.synthetic.main.activity_edit_bank_card.*

class BankEditCardActivity: AppCompatActivity() {

    var errorStack = ""

    lateinit var card: BankCard

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bank_card)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        card = TempRepository.card as BankCard

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        tv_bank_card_number.setText(card.number)
        tv_holder_name.setText("${card.name} ${card.surName}")
        tv_expiring_date.setText(card.validThru)
    }

    fun validateFields() {

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
                if(errorStack.isEmpty()) startActivity(Intent(this, OverviewCardActivity::class.java))
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