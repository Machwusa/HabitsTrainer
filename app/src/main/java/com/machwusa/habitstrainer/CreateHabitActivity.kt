package com.machwusa.habitstrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.java.simpleName

    private val CHOOSE_IMAGE_REQUEST = 1

    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun chooseImage(v: View) {
        val intent = Intent()

        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image for habit")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)

        Log.d(TAG, "Intent to choose an image was sent")
    }

    fun storeHabit(v: View) {
        if (et_title.isBlank() || et_descr.isBlank()){
            Log.d(TAG, "storeHabit: Title or desc missing")
            displayErrorMessage("Missing title or description")
            return
        }else if(imageBitmap == null){
            Log.d(TAG, "storeHabit: Image missing")
            displayErrorMessage("Missing image")
            return
        }

        //Store habit
        tv_error.visibility = View.INVISIBLE
    }

    private fun displayErrorMessage(message: String) {
        tv_error.text = message
        tv_error.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null){
            Log.d(TAG, "onActivityResult: An image was chosen by the user")

            val bitmap = readBitmap(data.data!!)

            bitmap?.let {
                this.imageBitmap = bitmap
                iv_image.setImageBitmap(bitmap)
                Log.d(TAG, "onActivityResult: Read bitmap and updated imageView")
            }
        }
    }

    private fun readBitmap(data: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        }catch (e: IOException){
            e.printStackTrace()
            null
        }
    }


}

private fun EditText.isBlank() = this.text.toString().isBlank()