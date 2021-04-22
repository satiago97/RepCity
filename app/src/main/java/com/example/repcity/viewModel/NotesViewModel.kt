package com.example.repcity.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.repcity.db.NoteDB
import com.example.repcity.db.NoteRepository
import com.example.repcity.entities.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application){

    lateinit var getNoteById: LiveData<Notes>

    private val repository: NoteRepository

    var allNotes: LiveData<List<Notes>>


    init {
        val notesDao = NoteDB.getDatabase(application, viewModelScope).notesDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }


    fun insert(note: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }

    fun delete(note: Notes) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteNote(note)
    }


    fun update(id: Int, titulo: String, descricao: String) = viewModelScope.launch(Dispatchers.IO){
        repository.update(id, titulo, descricao)
    }




}