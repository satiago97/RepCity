package com.example.repcity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.repcity.entities.Notes
import com.example.repcity.viewModel.NotesViewModel

class EditNote : AppCompatActivity() {



    lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)



    }




}