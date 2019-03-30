package com.infinity_coder.hackatonapp.presentation.edit_card.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.infinity_coder.hackatonapp.IMAGE_PATH_KEY
import com.infinity_coder.hackatonapp.SCAN_REQUEST_CODE
import com.infinity_coder.hackatonapp.presentation.scan.view.ScanActivity
import kotlinx.android.synthetic.main.activity_edit_card.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager




class EditCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.infinity_coder.hackatonapp.R.layout.activity_edit_card)

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivityForResult(intent, SCAN_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE) {
            Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show()
            val imagePath = data?.getStringExtra(IMAGE_PATH_KEY)
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
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun runTextRecognition(mSelectedImage: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
//        mTextButton.setEnabled(false)
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                //                mTextButton.setEnabled(true)
                processTextRecognitionResult(texts)
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                //                        mTextButton.setEnabled(true)
                e.printStackTrace()
            }
    }

    private fun processTextRecognitionResult(texts: FirebaseVisionText) {
        var couldRecognizeCardNumber = false
        var couldRecognizeBankCardNumber = false
        var couldRecognizeHolderName = false
        var couldRecognizeExpiringDate = false
        val blocks = texts.textBlocks
//        mTextView.setText(texts.text)
        if (blocks.size == 0) {
//            showToast("No text found")
            return
        }
//        mGraphicOverlay.clear()
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                val elements = lines[j].elements
                if (lines[j].text.replace(" ", "").matches("\\d{10}".toRegex())) {
                    tv_card_number.setText(lines[j].text)
                    couldRecognizeCardNumber = true
                }
                if (lines[j].text.matches("[A-Z]+\\s([A-Z])+".toRegex())) {
                    tv_holder_name.setText(lines[j].text)
                    couldRecognizeHolderName = true
                }
                if (lines[j].text.replace(" ", "")
                        .matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$".toRegex())
                ) {
                    tv_bank_card_number.setText(lines[j].text)
                    couldRecognizeBankCardNumber = true
                }
                for (k in elements.indices) {
//                    val textGraphic = TextGraphic(mGraphicOverlay, elements[k])
//                    mGraphicOverlay.add(textGraphic)
                    if (elements[k].text.matches(Regex("^\\d{2}\\/\\d{2}$"))) {
                        tv_expiring_date.setText(elements[k].text)
                        couldRecognizeExpiringDate = true
                    }
                }
            }
        }
        if (!couldRecognizeCardNumber)
            tv_card_number.setText("Couldn't recognize")
        if (!couldRecognizeBankCardNumber)
            tv_bank_card_number.setText("Couldn't recognize")
        if (!couldRecognizeHolderName)
            tv_holder_name.setText("Couldn't recognize")
        if (!couldRecognizeExpiringDate)
            tv_expiring_date.setText("Couldn't recognize")
    }

    private fun runCloudTextRecognition(mSelectedImage: Bitmap) {
//        mCloudButton.setEnabled(false)
        val image = FirebaseVisionImage.fromBitmap(mSelectedImage)
        val recognizer = FirebaseVision.getInstance()
            .cloudTextRecognizer
        recognizer.processImage(image)
            .addOnSuccessListener { texts ->
                //                mCloudButton.setEnabled(true)
                processCloudTextRecognitionResult(texts)
            }
            .addOnFailureListener(
                object : OnFailureListener {
                    override fun onFailure(@NonNull e: Exception) {
                        // Task failed with an exception
//                        mCloudButton.setEnabled(true)
                        e.printStackTrace()
                    }
                })
    }

    private fun processCloudTextRecognitionResult(text: FirebaseVisionText?) {
        var couldRecognizeCardNumber = false
        var couldRecognizeBankCardNumber = false
        var couldRecognizeHolderName = false
        var couldRecognizeExpiringDate = false
        // Task completed successfully
        if (text == null) {
//            showToast("No text found")
            return
        }
//        mGraphicOverlay.clear()

        val blocks = text.textBlocks
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                if (lines[j].text.replace(" ", "").matches("\\d{10}".toRegex())) {
                    tv_card_number.setText(lines[j].text)
                    couldRecognizeCardNumber = true
                }
                if (lines[j].text.matches("[A-Z]+\\s([A-Z])+".toRegex())) {
                    tv_holder_name.setText(lines[j].text)
                    couldRecognizeHolderName = true
                }
                if (lines[j].text.replace(" ", "")
                        .matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$".toRegex())
                ) {
                    tv_bank_card_number.setText(lines[j].text)
                    couldRecognizeBankCardNumber = true
                }

                //                showToast(lines.get(j).getText());
                val elements = lines[j].elements
                for (l in elements.indices) {
                    //                    CloudTextGraphic cloudDocumentTextGraphic = new CloudTextGraphic(mGraphicOverlay,
                    //                            words.get(l));
                    //                    mGraphicOverlay.add(cloudDocumentTextGraphic);
                    if (elements[l].text.matches(Regex("^\\d{2}\\/\\d{2}$"))) {
                        tv_expiring_date.setText(elements[l].text)
                        couldRecognizeExpiringDate = true
                    }
                }
            }
        }
        if (!couldRecognizeCardNumber)
            tv_card_number.setText("Couldn't recognize")
        if (!couldRecognizeBankCardNumber)
            tv_bank_card_number.setText("Couldn't recognize")
        if (!couldRecognizeHolderName)
            tv_holder_name.setText("Couldn't recognize")
        if (!couldRecognizeExpiringDate)
            tv_expiring_date.setText("Couldn't recognize")
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null
    }
}