package com.example.repcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val notesButton = findViewById<Button>(R.id.notesButton)
        notesButton.setOnClickListener {
            val intent = Intent(this, ListNotes::class.java)
            startActivity(intent)
        }
    }
}