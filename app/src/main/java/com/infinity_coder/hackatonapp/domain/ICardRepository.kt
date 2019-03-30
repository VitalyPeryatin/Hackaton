package com.infinity_coder.hackatonapp.domain

import androidx.lifecycle.LiveData
import com.infinity_coder.hackatonapp.data.db.entity.BankCard

interface ICardRepository {
    fun getCards() : LiveData<List<BankCard>>
    fun getCards(company:String) : LiveData<List<BankCard>>
}