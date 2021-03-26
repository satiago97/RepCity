package com.example.repcity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class AddNotes : AppCompatActivity() {

    private lateinit var titleText: EditText
    private lateinit var descriptionText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)


        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
           val intent = Intent(this, ListNotes::class.java)
            startActivity(intent)

        }


        titleText = findViewById(R.id.submitNoteTitle)
        descriptionText = findViewById(R.id.submitNoteDescription)

        val button = findViewById<Button>(R.id.submitNoteButton)
        button.setOnClickListener {
            val replyIntent = Intent()
            if(TextUtils.isEmpty(titleText.text) || TextUtils.isEmpty(descriptionText.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)

            }
            else{
                val title = titleText.text.toString()
                val description = descriptionText.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_TITLE, title)
                replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION, description)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_TITLE = "com.example.android.title"
        const val EXTRA_REPLY_DESCRIPTION = "com.example.android.description"
    }
}