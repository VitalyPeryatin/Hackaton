package com.infinity_coder.hackatonapp.domain

import androidx.lifecycle.LiveData
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard

interface ICardRepository {
    fun insert(abstractCard: AbstractCard)
    fun delete(abstractCard: AbstractCard)
    fun getBankCards() : LiveData<List<BankCard>>
    fun getBankCards(company:String) : LiveData<List<BankCard>>
    fun getFuelCard(): LiveData<List<FuelCard>>
    fun getFuelCard(company: String): LiveData<List<FuelCard>>
}