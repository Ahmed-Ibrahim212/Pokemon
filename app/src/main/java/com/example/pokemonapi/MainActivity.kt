package com.example.pokemonapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myuploadingapp.UploadImageActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ExtendedFloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(this, UploadImageActivity::class.java)
            startActivity(intent)
        }
    }
}