package com.putragandad.noteschallenge4.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.putragandad.noteschallenge4.data.Notes
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
public abstract class NotesRoomDatabase : RoomDatabase() {
    abstract fun notesDao() : NotesDao

    companion object {
        // Singleton instance of the database
        @Volatile
        private var INSTANCE: NotesRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : NotesRoomDatabase {
            // if the INSTANCE is not null, then return it
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDatabase::class.java,
                    "notes_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }


    }

}