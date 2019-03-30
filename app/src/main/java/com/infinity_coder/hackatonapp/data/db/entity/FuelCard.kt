package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "fuelCards")
class FuelCard(number: String, validThru: Date, company: String, path:String) : AbstractCard(number, validThru, company, path)