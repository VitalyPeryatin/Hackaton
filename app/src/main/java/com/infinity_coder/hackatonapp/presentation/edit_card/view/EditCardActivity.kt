package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.infinity_coder.hackatonapp.*
import com.infinity_coder.hackatonapp.data.db.entity.BankCard
import com.infinity_coder.hackatonapp.data.db.entity.FuelCard
import com.infinity_coder.hackatonapp.data.repository.TempRepository
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import java.util.*

class EditCardActivity: AppCompatActivity() {
    val tempRepository = TempRepository
    var cardNumber = ""
    var holderName = ""
    var bankCardNumber = ""
    var expiringDate = ""
    var imagePath = ""
    var company = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)
        startActivityForResult(
            Intent(this, ScanActivity::class.java),
            SCAN_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imagePath = data?.getStringExtra(IMAGE_PATH_KEY)!!
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            val bitmap = BitmapFactory.decodeFile(imagePath, options)


            if (isNetworkConnected()) {
                runCloudTextRecognition(bitmap)
                showToast("Running cloud text recognition")
            } else {
                runTextRecognition(bitmap)
                showToast("Running on-device text recognition")
            }
        } else {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun runTextRecognition(mSelectedImage: Bitmap) {
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

    private fun processTextRecognitionResult(texts: FirebaseVisionText) {
        val blocks = texts.textBlocks

        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                if (lines[j].text.matches(regexFuelCardName)) {
                    cardNumber = (lines[j].text)
                }
                if (lines[j].text.matches(regexHolderName)) {
                    holderName = (lines[j].text)
                }
                if (lines[j].text.replace(" ", "")
                        .matches(regexBankCardName)
                ) {
                    bankCardNumber = (lines[j].text)
                }

                val elements = lines[j].elements
                for (l in elements.indices) {
                    if (elements[l].text.matches(regexDate)) {
                        expiringDate = (elements[l].text)
                    }
                    when {
                        elements[l].text == "VISA" -> company = "Visa"
                        elements[l].text == "MasterCard" -> company = "MasterCard"
                    }
                }
            }
        }
        if (holderName != ""){
            tempRepository.card = BankCard(cardNumber, expiringDate, "", holderName.split(" ")[0], holderName.split(" ")[1])
        }
        else{
            tempRepository.card = FuelCard(cardNumber, expiringDate, "")
        }

        if (holderName != "") {
            TempRepository.card =
                BankCard(cardNumber, expiringDate, company, holderName.split(" ")[0], holderName.split(" ")[1])
        } else {
            TempRepository.card = FuelCard(cardNumber, expiringDate, "")
        }
        val intent = if (TempRepository.card is BankCard)
            Intent(this, BankEditCardActivity::class.java)
        else
            Intent(this, FuelEditCardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun runCloudTextRecognition(mSelectedImage: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .cloudTextRecognizer
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                processCloudTextRecognitionResult(texts)
            }
            .addOnFailureListener { e ->
                showToast("Bank card not recognized!")
                e.printStackTrace()
                finish()
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
                if (lines[j].text.matches(regexBankCardName)
                ) {
                    bankCardNumber = (lines[j].text)
                }

                val elements = lines[j].elements
                for (l in elements.indices) {
                    if (elements[l].text.matches(regexDate)) {
                        expiringDate = (elements[l].text)
                    }
                    when {
                        elements[l].text == "VISA" -> company = "Visa"
                        elements[l].text == "MasterCard" -> company = "MasterCard"
                    }
                }
            }
        }

        if (holderName != "") {
            TempRepository.card =
                BankCard(bankCardNumber, expiringDate, company, holderName.split(" ")[0], holderName.split(" ")[1])
        } else {
            TempRepository.card = FuelCard(cardNumber, expiringDate, "")
        }
        val intent = if (TempRepository.card is BankCard)
            Intent(this, BankEditCardActivity::class.java)
        else
            Intent(this, FuelEditCardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }
}