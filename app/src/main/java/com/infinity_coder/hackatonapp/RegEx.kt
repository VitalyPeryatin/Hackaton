package com.infinity_coder.hackatonapp

val regexBankCardName = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$".toRegex()
val regexHolderName = "[A-Z]+\\s([A-Z])+".toRegex()
val regexFuelCardName = "\\d{10}".toRegex()
val regexDate = "^\\d{2}/\\d{2}$".toRegex()