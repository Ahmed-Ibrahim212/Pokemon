package com.example.pokemonapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myuploadingapp.MainActivity2
//import com.example.myuploadingapp.MainActivity2
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
//            val intent = Intent(this, MainActivity2::class.java)
//            startActivity(intent)
//        }
    }
}