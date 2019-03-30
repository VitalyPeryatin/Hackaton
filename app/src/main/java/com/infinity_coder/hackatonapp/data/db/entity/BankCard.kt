package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity

@Entity(tableName = "bankCards")
class BankCard(number: String = "", validThru: String = "",
               company: String = "", var name: String = "", var surName: String = "", path: String = "") :
    AbstractCard(number, validThru, company, path)

/*data class MonthYear(val month: Int, val year: Int){
    override fun toString(): String {
        return "$month/$year"
    }
}*/
