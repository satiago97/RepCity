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

    suspend fun updateNote(notes: Notes){
        notesDao.updateNote(notes)
    }

    suspend fun update(id: Int, titulo: String, descricao: String){
        notesDao.update(id, titulo, descricao)
    }





}