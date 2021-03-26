package com.example.repcity.db

import androidx.lifecycle.LiveData
import com.example.repcity.dao.NotesDao
import com.example.repcity.entities.Notes

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = notesDao.getAllNotesById()


    suspend fun insert(notes: Notes) {
        notesDao.insert(notes)
    }

    suspend fun deleteNote(notes: Notes){
        notesDao.delete(notes)
    }

    suspend fun update(notes: Notes){
        notesDao.update(notes)
    }


    fun editById(id: Int) = notesDao.getNoteById(id)
}