package com.infinity_coder.hackatonapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.infinity_coder.hackatonapp.data.CardDao
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard

const val DATABASE_NAME = "cards_db"
@Database(entities = [BankCard::class, FuelCard::class], version = 1, exportSchema = false)
abstract class CardDatabase() : RoomDatabase(){
    abstract fun cardDao(): CardDao
}

/*
const val DATABASE_NAME = "dishes"

@Database(entities = [Dish::class, GroupEntity::class, DayOfWeekEntity::class, BasketEntity::class], version = 3, exportSchema = false)
abstract class DishDatabase: RoomDatabase() {
    abstract fun dishDao(): DishDao
}
 */