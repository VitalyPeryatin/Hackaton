package com.infinity_coder.hackatonapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: FuelCard)
    @Delete
    fun delete(card: FuelCard)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: BankCard)
    @Delete
    fun delete(card: BankCard)
    @Query("SELECT * FROM BankCard")
    fun getBankCards() : LiveData<List<BankCard>>
    @Query("SELECT * FROM BankCard WHERE company = :companyName")
    fun getBankCards(companyName: String) : LiveData<List<BankCard>>
    @Query("SELECT * FROM FuelCard")
    fun getFuelCards() : LiveData<List<FuelCard>>
    @Query("SELECT * FROM FuelCard WHERE company = :companyName")
    fun getFuelCards(companyName: String) : LiveData<List<FuelCard>>

}