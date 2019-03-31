package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.domain.ICardRepository
import com.infinity_coder.hackatonapp.presentation.card_overview.view.OverviewCardActivity
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import com.infinity_coder.hackatonapp.regexDate
import com.infinity_coder.hackatonapp.regexFuelCardName
import kotlinx.android.synthetic.main.fragment_edit_fuel_card.*

class FuelEditCardFragment: Fragment() {

    private var errorStack = ""
    lateinit var cardRepository: ICardRepository
    lateinit var card: FuelCard

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_fuel_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_close)

        card = TempRepository.card as FuelCard

        tv_bank_card_number.setText(card.subNumber)
        tv_card_number.setText(card.number)
        tv_expiring_date.setText(card.validThru)
        etCompany.setText(card.company)

        fabCapturePhoto.setOnClickListener {
            startActivity(Intent(context, ScanActivity::class.java))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Топливная карта"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun validateFields() {

//        if(!tv_bank_card_number.text.toString().matches(regexBankCardNameCheck)) errorStack += "Неправильно введён банковский номер карты\n\n"
        if(!tv_card_number.text.toString().matches(regexFuelCardName)) errorStack += "Неправильно введён номер карты\n\n"
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
                if (errorStack.isEmpty()) {
                    card = TempRepository.card as FuelCard

                    card.number = tv_card_number.text.toString()
                    card.subNumber = tv_bank_card_number.text.toString()
                    card.validThru = tv_expiring_date.text.toString()
                    card.company = etCompany.text.toString()
                    cardRepository.insert(card)
                    startActivity(Intent(context, OverviewCardActivity::class.java))
                    activity?.finish()
                } else showError(errorStack); errorStack = ""
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