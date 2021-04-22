package com.example.repcity.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.repcity.entities.Notes


@Dao
interface NotesDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotesById(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)
    
    @Query("UPDATE NOTE_TABLE SET title = :titulo, description = :descricao WHERE id = :id")
    suspend fun update(id: Int, titulo: String, descricao: String)

    @Query("SELECT * FROM note_table WHERE id= :id")
    fun getNoteById(id: Int): LiveData<Notes>


}

