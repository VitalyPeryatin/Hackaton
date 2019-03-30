package com.infinity_coder.hackatonapp

val regexBankCardName = "^\\d{16}\$".toRegex()
val regexHolderName = "[A-Z]+\\s([A-Z])+".toRegex()
val regexFuelCardName = "\\d{10}".toRegex()
val regexDate = "^\\d{2}/\\d{2}$".toRegex()
