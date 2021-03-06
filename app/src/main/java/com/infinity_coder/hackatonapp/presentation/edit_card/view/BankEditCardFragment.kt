package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.repository.CardRepository
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.domain.ICardRepository
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import com.infinity_coder.hackatonapp.regexBankCardNameCheck
import com.infinity_coder.hackatonapp.regexDate
import com.infinity_coder.hackatonapp.regexHolderName
import kotlinx.android.synthetic.main.fragment_edit_bank_card.*

class BankEditCardFragment: Fragment() {
    var errorStack = ""

    lateinit var cardRepository: ICardRepository
    lateinit var card: BankCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_bank_card, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cardRepository = CardRepository()
        card = TempRepository.card as BankCard

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(context, ScanActivity::class.java))
        }

        tv_bank_card_number.setText(card.number)
        tv_holder_name.setText(card.name)
        tv_expiring_date.setText(card.validThru)
        etCompany.setText(card.company)

        tv_holder_name.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        tv_bank_card_number.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        tv_expiring_date.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        etCompany.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        (activity as AppCompatActivity).supportActionBar?.title = "Банковская карта"
    }

    private fun validateFields() {

        if(!tv_bank_card_number.text.toString().matches(regexBankCardNameCheck)) errorStack += "Неправильно введён номер карты\n\n"
        if(!tv_holder_name.text.toString().matches(regexHolderName)) errorStack += "Неправильно введено имя держателя карты\n\n"
        if(!tv_expiring_date.text.toString().matches(regexDate)) errorStack += "Неправильно введён срок действия\n\n"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_accept, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
            R.id.accept -> {
                validateFields()
                if(errorStack.isEmpty()){
                    card.number = tv_bank_card_number.text.toString()
                    card.validThru = tv_expiring_date.text.toString()
                    card.company = etCompany.text.toString()
                    card.name = tv_holder_name.text.toString()
                    Log.d("BEAC", card.number + card.validThru)
                    cardRepository.insert(card)
                    startActivity(Intent(context, OverviewCardActivity::class.java))
                    activity?.finish()
                }
                else showError(errorStack); errorStack = ""
            }
        }
        return true
    }

    private fun showError(err:String){
        val mErrorDialog = AlertDialog.Builder(context!!)
            .setTitle(R.string.app_name)
            .setMessage(err)
            .show()
        mErrorDialog.setOnCancelListener{
            if (mErrorDialog != null && mErrorDialog.isShowing) mErrorDialog.cancel()
        }
    }
}