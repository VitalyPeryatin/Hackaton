package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "fuelCards")
class FuelCard(number: String = "", validThru: Date = Date(),
               company: String = "", path:String = "",
               val subNumber: String? = null) : AbstractCard(number, validThru, company, path)