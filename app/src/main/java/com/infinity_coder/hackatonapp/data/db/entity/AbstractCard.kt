package com.infinity_coder.hackatonapp.data.db.entity

import androidx.room.PrimaryKey
import java.util.*

abstract class AbstractCard(@PrimaryKey val number: Int, val validThru: Date, val company: String)