package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cards")
data class BankCard(@PrimaryKey val number: Int, val name: String, val surName: String, val validThru: Date, val company: String)


/*data class MonthYear(val month: Int, val year: Int){
    override fun toString(): String {
        return "$month/$year"
    }
}*/
