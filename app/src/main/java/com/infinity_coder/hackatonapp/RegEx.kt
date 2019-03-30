package com.infinity_coder.hackatonapp

val regexBankCardName = "\\d{3,4}\\s?\\d{4}\\s?\\d{4}\\s?\\d{3,4}".toRegex()
val regexHolderName = "[A-Z]+\\s([A-Z])+".toRegex()
val regexFuelCardName = "\\d\\s?\\d\\s?\\d\\s?\\d\\s?\\d\\s?\\d\\s?\\d\\s?\\d\\s?\\d\\s?\\d".toRegex()
val regexDate = "\\d{2}/\\d{2}".toRegex()
