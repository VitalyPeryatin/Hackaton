package com.infinity_coder.hackatonapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.infinity_coder.hackatonapp.App
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.domain.ICardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardRepository : ICardRepository {
    private val cardDao = App.cardDb.cardDao()

    override fun insert(bankCard: BankCard) {
        GlobalScope.launch(Dispatchers.IO) {
            cardDao.insert(bankCard)
        }
    }

    override fun getBankCardsList() : List<BankCard> = runBlocking(Dispatchers.IO){
        return@runBlocking cardDao.getBankCardsList()
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

    override fun getAdapterCards(): LiveData<List<AbstractCard>> {
        val mediatorLiveData = MediatorLiveData<List<AbstractCard>>()
        mediatorLiveData.addSource(cardDao.getBankCards(), Observer {
            mediatorLiveData.value = it
        })
        mediatorLiveData.addSource(cardDao.getFuelCards(), Observer {
            mediatorLiveData.value = it
        })
        return mediatorLiveData
    }

    object temp{


    }
}