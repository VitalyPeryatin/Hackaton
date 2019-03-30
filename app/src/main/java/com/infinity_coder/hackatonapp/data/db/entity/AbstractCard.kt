package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.PrimaryKey

abstract class AbstractCard(@PrimaryKey val number: String = "",
                            val validThru: String = "", val company: String = "", var path:String = "")