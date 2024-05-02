package com.putragandad.noteschallenge4.repositories

import androidx.annotation.WorkerThread
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.db.NotesDao
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesDao) {

    // Observe flow from the list of notes
    val allNotes: Flow<List<Notes>> = notesDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getNotesByUser(email: String) {
        notesDao.getNotesByUser(email)
    }

    suspend fun insert(notes: Notes) {
        notesDao.insert(notes)
    }

    suspend fun delete(notes: Notes) {
        notesDao.delete(notes)
    }

    suspend fun update(notes: Notes) {
        notesDao.update(notes)
    }


}