package com.infinity_coder.hackatonapp.presentation.edit_card.view_model

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.regexBankCardName
import com.infinity_coder.hackatonapp.regexDate
import com.infinity_coder.hackatonapp.regexFuelCardName
import com.infinity_coder.hackatonapp.regexHolderName

class EditViewModel: ViewModel() {
    var cardNumber = ""
    var holderName = ""
    var bankCardNumber = ""
    var expiringDate = ""
    var imagePath = ""
    var company = ""

    val navigatoLive = MutableLiveData<AbstractCard>()
    val errorListener = MutableLiveData<Boolean>()

    fun runTextRecognition(mSelectedImage: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                processTextRecognitionResult(texts)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun processTextRecognitionResult(text: FirebaseVisionText) {
        val blocks = text.textBlocks
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                if (lines[j].text.matches(regexFuelCardName)) {
                    cardNumber = (lines[j].text)
                }
                if (lines[j].text.matches(regexHolderName)) {
                    holderName = (lines[j].text)
                }
                if (lines[j].text.replace('b', '6').matches(regexBankCardName)
                ) {
                    bankCardNumber = lines[j].text.replace('b', '6')
                }

                val elements = lines[j].elements
                for (l in elements.indices) {
                    if (elements[l].text.replace('S', '5').matches(regexDate)) {
                        expiringDate = elements[l].text.replace('S', '5')
                    }
                    if (elements[l].text.replace('S', '5').contains('/')) {
                        val slashPos = elements[l].text.indexOf('/')
                        if (slashPos - 2 >= 0 && slashPos + 3 < lines[j].text.length)
                            expiringDate = elements[l].text.substring(slashPos - 2, slashPos + 3)


                    }
                    when {
                        elements[l].text == "VISA" -> company = "Visa"
                        elements[l].text == "MasterCard" -> company = "MasterCard"
                        elements[l].text == "mastercard" -> company = "MasterCard"
                    }
                }
            }
        }

        if (!holderName.isEmpty()) {
            TempRepository.card = BankCard(bankCardNumber, expiringDate, company, holderName, " ", imagePath)
        } else {
            TempRepository.card = FuelCard(cardNumber, expiringDate, company, imagePath)
        }

        navigatoLive.postValue(TempRepository.card)

    }


    fun runCloudTextRecognition(mSelectedImage: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .cloudTextRecognizer
        FirebaseVisionCloudTextRecognizerOptions.Builder().setLanguageHints(listOf("en")).build()
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                processCloudTextRecognitionResult(texts)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                errorListener.postValue(true)
            }
    }

    private fun processCloudTextRecognitionResult(text: FirebaseVisionText) {

        val blocks = text.textBlocks
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                if (lines[j].text.matches(regexFuelCardName)) {
                    cardNumber = (lines[j].text)
                }
                if (lines[j].text.matches(regexHolderName)) {
                    holderName = (lines[j].text)
                }
                if (lines[j].text.matches(regexBankCardName)) {
                    bankCardNumber = (lines[j].text)
                    if (lines[j].text.replace('b', '6').matches(regexBankCardName)
                    ) {
                        bankCardNumber = lines[j].text.replace('b', '6')
                    }

                    val elements = lines[j].elements
                    for (l in elements.indices) {
                        if (elements[l].text.replace('S', '5').matches(regexDate)) {
                            expiringDate = elements[l].text.replace('S', '5')
                        }
                        if (elements[l].text.replace('S', '5').contains('/')) {
                            val slashPos = elements[l].text.indexOf('/')
                            if (slashPos - 2 >= 0 && slashPos + 3 < lines[j].text.length)
                                expiringDate = elements[l].text.substring(slashPos - 2, slashPos + 3)


                        }
                        when {
                            elements[l].text == "VISA" -> company = "Visa"
                            elements[l].text == "MasterCard" -> company = "MasterCard"
                            elements[l].text == "mastercard" -> company = "MasterCard"
                        }
                    }
                }
            }
        }

        if (!holderName.isEmpty()) {
            TempRepository.card = BankCard(bankCardNumber, expiringDate, company, holderName, " ", imagePath)
        } else {
            TempRepository.card = FuelCard(cardNumber, expiringDate, company, imagePath)
        }

        navigatoLive.postValue(TempRepository.card)
    }


}