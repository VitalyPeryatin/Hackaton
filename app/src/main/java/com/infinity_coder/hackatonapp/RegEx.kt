package com.infinity_coder.hackatonapp

val regexBankCardName = "[A-Z]{4}\\s?[A-Z]{4}\\s?[A-Z]{4}\\s?[A-Z]{4}".toRegex()
val regexHolderName = "[A-Z]+\\s([A-Z])+".toRegex()
val regexFuelCardName = "\\d{10}".toRegex()
val regexDate = "\\d{2}/\\d{2}".toRegex()
