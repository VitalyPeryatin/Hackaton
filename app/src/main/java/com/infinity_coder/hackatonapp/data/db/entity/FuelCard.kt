package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "fuelCards")
class FuelCard(number: Int, validThru: Date, company: String) : AbstractCard(number, validThru, company)