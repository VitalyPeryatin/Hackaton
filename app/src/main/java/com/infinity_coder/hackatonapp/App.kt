package com.infinity_coder.hackatonapp

import android.app.Application
import androidx.room.Room
import com.infinity_coder.hackatonapp.data.db.CardDatabase
import com.infinity_coder.hackatonapp.data.db.DATABASE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {

    override fun onCreate() {
        super.onCreate()



        cardDb = Room.databaseBuilder(this, CardDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
        GlobalScope.launch(Dispatchers.IO) {
            //DBCreator.initDB()
        }
    }

    companion object {
        lateinit var cardDb: CardDatabase
            private set
    }
}
