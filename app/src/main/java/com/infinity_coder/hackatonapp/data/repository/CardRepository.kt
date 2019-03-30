package com.infinity_coder.hackatonapp.data.repository

import androidx.lifecycle.LiveData
import com.infinity_coder.hackatonapp.App
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.domain.ICardRepository

class CardRepository : ICardRepository {
    private val cardDao = App.cardDb.cardDao()

    override fun insert(abstractCard: AbstractCard) {
        return cardDao.insert(abstractCard)
    }

    override fun delete(abstractCard: AbstractCard) {
        return cardDao.delete(abstractCard)
    }

    override fun getBankCards(): LiveData<List<BankCard>> {
        return cardDao.getBankCards()
    }

    override fun getBankCards(company: String): LiveData<List<BankCard>> {
        return cardDao.getBankCards(company)
    }

    override fun getFuelCard(): LiveData<List<FuelCard>>{
        return cardDao.getFuelCards()
    }
    override fun getFuelCard(company: String): LiveData<List<FuelCard>>{
        return cardDao.getFuelCards(company)
    }



}