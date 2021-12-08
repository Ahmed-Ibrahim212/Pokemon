package com.example.myuploadingapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokemonapi.R
import com.example.pokemonapi.UploadApi.ImageUploadAPI
import com.example.pokemonapi.UploadApi.UploadRequestBody
import com.example.pokemonapi.UploadApi.UploadResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity2 : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private var selectedImage: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //checking for permission
        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
            } else {
                openImageChooser()
            }
        }
        // uploading image to end point
        findViewById<Button>(R.id.uploadImage).setOnClickListener {
            upLoadImage()
        }
    }

    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        } else {
            openImageChooser()
        }
    }

    //uploading the image to the server
    private fun upLoadImage() {
        if (selectedImage == null) {
            findViewById<View>(R.id.layout_root).snackbar("Select an Image First")
            return
        }

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImage!!, "r", null) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImage!!))
        val outPutStream = FileOutputStream(file)
        inputStream.copyTo(outPutStream)

        //creating progress for upload
        findViewById<ProgressBar>(R.id.progressBar).progress = 0
        val body = UploadRequestBody(file, "image", this)

        ImageUploadAPI().uploadImage(
            MultipartBody.Part.createFormData("image", file.name, body),
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                "Image from Device"
            )
        ).enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                findViewById<ProgressBar>(R.id.progressBar).progress = 100
                findViewById<ConstraintLayout>(R.id.layout_root).snackbar(response.body()?.message.toString())
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                findViewById<ConstraintLayout>(R.id.layout_root).snackbar(t.message!!)
            }
        })
    }


    // open the image chooser
    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    selectedImage = data?.data
                    findViewById<ImageView>(R.id.imageView).setImageURI(selectedImage)
                }
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        findViewById<ProgressBar>(R.id.progressBar).progress = percentage
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }
    // request the permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_IMAGE_PICKER) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissions()
            } else {
                findViewById<ConstraintLayout>(R.id.layout_root).snackbar("Permissions Not Granted")
            }
        }
    }


}

