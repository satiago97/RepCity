package com.example.repcity.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.repcity.dao.NotesDao
import com.example.repcity.entities.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
public abstract class NoteDB : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database ->
                scope.launch {
                    var notesDao = database.notesDao()


                    var note = Notes(1, "Tiago", "Bom dia")
                    notesDao.insert(note)
                    var note2 = Notes(2, "Nazare", "Boa noite")
                    notesDao.insert(note2)



                }
            }
        }

    }

    companion object {

        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java, "notes_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}