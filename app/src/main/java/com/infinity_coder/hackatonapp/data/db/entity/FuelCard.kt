package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.Entity

@Entity
class FuelCard(number: String = "", validThru: String = "",
               company: String = "", path:String = "",
               var subNumber: String? = null) : AbstractCard(number, validThru, company, path)