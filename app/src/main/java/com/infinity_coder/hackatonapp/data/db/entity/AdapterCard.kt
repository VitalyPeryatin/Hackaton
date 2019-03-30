package com.infinity_coder.hackatonapp.data.db.entity

/*@Entity(tableName = "adapter_card", foreignKeys = arrayOf(ForeignKey(entity = BankCard::class, parentColumns = arrayOf("number"), childColumns = arrayOf("number")),
    ForeignKey(entity = FuelCard::class, parentColumns = arrayOf("number"), childColumns = arrayOf("number"))))
data class AdapterCard(@PrimaryKey val number: String, val path: String)*/
data class AdapterCard(val number: String, val path: String)