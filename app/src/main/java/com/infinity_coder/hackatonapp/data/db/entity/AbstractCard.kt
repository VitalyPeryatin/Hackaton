package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.PrimaryKey

abstract class AbstractCard(@PrimaryKey var number: String = "",
                            var validThru: String = "", var company: String = "", var path:String = "")