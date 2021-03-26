package com.example.repcity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repcity.adapters.INotesRVAdapter
import com.example.repcity.adapters.NotesAdapter
import com.example.repcity.entities.Notes
import com.example.repcity.viewModel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNotes : AppCompatActivity(), INotesRVAdapter {


    lateinit var notesViewModel: NotesViewModel

    private val newNoteActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)


        //backbutton
        val backButtonToMain = findViewById<Button>(R.id.backButtonNotes)
        backButtonToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        //recycler
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotesAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        notesViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotesViewModel::class.java)
        notesViewModel.allNotes.observe(this, Observer{
            notes-> notes?.let { adapter.setNotes(it) }
        })


    //fab button

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddNotes::class.java)
            startActivityForResult(intent, newNoteActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newNoteActivityRequestCode  && resultCode == Activity.RESULT_OK) {
            val ptitle = data?.getStringExtra(AddNotes.EXTRA_REPLY_TITLE)
            val pdescription = data?.getStringExtra(AddNotes.EXTRA_REPLY_DESCRIPTION)


            if(ptitle != null && pdescription != null){
                val note = Notes(title = ptitle, description = pdescription)
                notesViewModel.insert(note)
            }else{
                Toast.makeText(applicationContext, "Note not saved!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClicked(note: Notes) {
        notesViewModel.delete(note)
    }


}