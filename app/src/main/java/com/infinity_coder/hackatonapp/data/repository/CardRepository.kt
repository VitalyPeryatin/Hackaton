package com.infinity_coder.hackatonapp.data.repository

import androidx.lifecycle.LiveData
import com.infinity_coder.hackatonapp.App
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.domain.ICardRepository

class CardRepository : ICardRepository {
    private val cardDao = App.cardDb.cardDao()
    override fun getCards(): LiveData<List<BankCard>> {
        return cardDao.getCards()
    }

    override fun getCards(company: String): LiveData<List<BankCard>> {
        return cardDao.getCards(company)
    }
}