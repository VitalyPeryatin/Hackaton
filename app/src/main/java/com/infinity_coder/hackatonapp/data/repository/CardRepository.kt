package com.infinity_coder.hackatonapp.data.repository

import androidx.lifecycle.LiveData
import com.infinity_coder.hackatonapp.App
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.AdapterCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.domain.ICardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CardRepository : ICardRepository {
    private val cardDao = App.cardDb.cardDao()

    override fun insert(bankCard: BankCard) {
        cardDao.insert(bankCard)
    }

    override fun delete(bankCard: BankCard) {
        cardDao.delete(bankCard)
    }

    override fun insert(fuelCard: FuelCard) {
        cardDao.insert(fuelCard)
    }

    override fun delete(fuelCard: FuelCard) {
        cardDao.delete(fuelCard)
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

    override fun getAdapterCards(): List<AdapterCard>{
        val items = mutableListOf<AbstractCard>()
        val adapterItems = mutableListOf<AdapterCard>()
        runBlocking {
            val asyncBankCards = GlobalScope.async(Dispatchers.IO) {
                cardDao.getBankCards()
            }
            val asyncFuelCards = GlobalScope.async(Dispatchers.IO) {
                cardDao.getFuelCards()
            }
            items.addAll(asyncBankCards.await().value!!)
            items.addAll(asyncFuelCards.await().value!!) }
        items.map { adapterItems.add(AdapterCard(it.number, it.path)) }
        return adapterItems
    }
}