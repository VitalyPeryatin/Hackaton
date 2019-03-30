package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.PrimaryKey
import java.util.*

abstract class AbstractCard(@PrimaryKey val number: String = "",
                            val validThru: Date = Date(), val company: String = "", var path:String = "")