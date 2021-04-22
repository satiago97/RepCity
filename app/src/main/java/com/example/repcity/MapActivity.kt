package com.example.repcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.repcity.storage.SharedPrefManager

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        }
    }
}