package com.infinity_coder.hackatonapp.domain

import androidx.lifecycle.LiveData
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard

interface ICardRepository {
    fun insert(bankCard: BankCard)
    fun delete(bankCard: BankCard)
    fun insert(fuelCard: FuelCard)
    fun delete(fuelCard: FuelCard)
    fun getBankCards() : LiveData<List<BankCard>>
    fun getBankCards(company:String) : LiveData<List<BankCard>>
    fun getFuelCard(): LiveData<List<FuelCard>>
    fun getFuelCard(company: String): LiveData<List<FuelCard>>
    fun getAdapterCards():LiveData<List<AbstractCard>>
    fun getBankCardsList() : List<AbstractCard>
    fun getCardByNumber(number: String): AbstractCard?
}