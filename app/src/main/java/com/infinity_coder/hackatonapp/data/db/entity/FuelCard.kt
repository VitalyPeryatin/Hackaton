package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity

@Entity(tableName = "fuelCards")
class FuelCard(number: String = "", validThru: String = "",
               company: String = "", path:String = "",
               val subNumber: String? = null) : AbstractCard(number, validThru, company, path)