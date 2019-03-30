package com.infinity_coder.hackatonapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.infinity_coder.hackatonapp.data.CardDao
import com.infinity_coder.hackatonapp.data.db.converters.DateConverter
import com.infinity_coder.hackatonapp.data.db.entity.BankCard

const val DATABASE_NAME = "cards_db"
@Database(entities = [BankCard::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class CardDatabase: RoomDatabase(){
    abstract fun cardDao(): CardDao
}

/*
const val DATABASE_NAME = "dishes"

@Database(entities = [Dish::class, GroupEntity::class, DayOfWeekEntity::class, BasketEntity::class], version = 3, exportSchema = false)
abstract class DishDatabase: RoomDatabase() {
    abstract fun dishDao(): DishDao
}
 */