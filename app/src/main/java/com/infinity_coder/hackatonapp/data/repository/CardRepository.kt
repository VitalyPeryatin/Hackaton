package com.infinity_coder.hackatonapp.data.repository

import android.util.Log
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

    override fun insert(bankCard: BankCard) = runBlocking(Dispatchers.IO) {
        cardDao.insert(bankCard)
    }

    override fun delete(bankCard: BankCard) = runBlocking(Dispatchers.IO){
        cardDao.delete(bankCard)
    }
    override fun insert(fuelCard: FuelCard) = runBlocking(Dispatchers.IO){
        cardDao.insert(fuelCard)
    }

    override fun delete(fuelCard: FuelCard) = runBlocking {
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

    override fun getAdapterCards(): List<AdapterCard> {
        val items = mutableListOf<AbstractCard>()
        val adapterItems = mutableListOf<AdapterCard>()
        val bankCards : LiveData<List<BankCard>> = cardDao.getBankCards()
        val fuelCards : LiveData<List<FuelCard>> = cardDao.getFuelCards()
        if(bankCards.value != null && bankCards.value!!.isNotEmpty()) {
            Log.d("Repo", "Bank is not empty")
            items.addAll(bankCards.value!!)
        }
        if(fuelCards.value != null && fuelCards.value!!.isNotEmpty()) {
            Log.d("Repo", "Fuel is not empty")
            items.addAll(fuelCards.value!!)
        }
        items.map { adapterItems.add(AdapterCard(it.number, it.path)) }
        return adapterItems
    }

    object temp{


    }
}