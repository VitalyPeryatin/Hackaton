package com.infinity_coder.hackatonapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: AbstractCard)
    @Delete
    fun delete(card: AbstractCard)
    @Query("SELECT * FROM bankCards")
    fun getBankCards() : LiveData<List<BankCard>>
    @Query("SELECT * FROM bankCards WHERE company = :companyName")
    fun getBankCards(companyName: String) : LiveData<List<BankCard>>
    @Query("SELECT * FROM fuelCards")
    fun getFuelCards() : LiveData<List<FuelCard>>
    @Query("SELECT * FROM fuelCards WHERE company = :companyName")
    fun getFuelCards(companyName: String) : LiveData<List<FuelCard>>


}