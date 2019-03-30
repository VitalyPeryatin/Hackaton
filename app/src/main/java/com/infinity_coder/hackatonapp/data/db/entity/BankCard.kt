package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "bankCards")
class BankCard(number: String, validThru: Date, company: String, val name: String, val surName: String) : AbstractCard(number, validThru, company)

/*data class MonthYear(val month: Int, val year: Int){
    override fun toString(): String {
        return "$month/$year"
    }
}*/
