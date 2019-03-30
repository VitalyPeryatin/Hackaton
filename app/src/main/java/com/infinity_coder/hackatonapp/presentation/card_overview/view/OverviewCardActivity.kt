package com.infinity_coder.hackatonapp.presentation.card_overview.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.presentation.edit_card.view.EditCardActivity
import kotlinx.android.synthetic.main.activity_overview_card.*


class OverviewCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_card)
        buttonGoScan.setOnClickListener {
            startActivity(Intent(this, EditCardActivity::class.java))
        }
    }
}