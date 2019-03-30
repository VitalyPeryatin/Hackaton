package com.infinity_coder.hackatonapp.presentation.scan.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.infinity_coder.hackatonapp.IMAGE_PATH_KEY
import com.infinity_coder.hackatonapp.R
import com.otaliastudios.cameraview.Audio
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.Mode
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.activity_scan.*
import java.io.FileOutputStream
import java.util.*

class ScanActivity : AppCompatActivity() {

    private val maxBitmapWidth = 1500
    private val maxBitmapHeight = 1500

    private val cameraListener = object : CameraListener() {
        override fun onPictureTaken(result: PictureResult) {
            onPicture(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        setCameraView()
        fabCamera.setOnClickListener {
            cameraView.takePictureSnapshot()
        }
    }

    private fun setCameraView(){
        cameraView.setLifecycleOwner(this)
        cameraView.mode = Mode.PICTURE
        cameraView.audio = Audio.OFF
        cameraView.addCameraListener(cameraListener)
    }

    private fun onPicture(result: PictureResult) {
        result.toBitmap(maxBitmapWidth, maxBitmapHeight) { bitmap ->
            if(bitmap != null) {
                val horizontalBitmap = setHorizontalOrientation(bitmap)
                val imagePath = saveBitmapToDir(horizontalBitmap, "$filesDir")
                val intent = Intent()
                intent.putExtra(IMAGE_PATH_KEY, imagePath)
                setResult(Activity.RESULT_OK, intent)
            }
            else
                setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        }
    }

    private fun setHorizontalOrientation(bitmap: Bitmap): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(180f)
        return if(bitmap.width > bitmap.height)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        else
            bitmap
    }

  private fun saveBitmapToDir(bitmap: Bitmap?, parentDir: String): String {
        var out: FileOutputStream? = null
        val pathName = "$parentDir/image-${UUID.randomUUID()}.png"
        try {
            out = FileOutputStream(pathName)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            out?.close()
        }
        return pathName
    }

}