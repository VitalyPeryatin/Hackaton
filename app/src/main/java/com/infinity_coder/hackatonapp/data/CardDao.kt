package com.infinity_coder.hackatonapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.infinity_coder.hackatonapp.data.db.entity.BankCard

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: BankCard)
    @Delete
    fun delete(card: BankCard)
    @Query("SELECT * FROM cards")
    fun getCards() : LiveData<List<BankCard>>
    @Query("SELECT * FROM cards WHERE company = :companyName")
    fun getCards(companyName: String) : LiveData<List<BankCard>>
}