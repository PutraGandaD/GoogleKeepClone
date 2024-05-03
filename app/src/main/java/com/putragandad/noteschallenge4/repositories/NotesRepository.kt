package com.putragandad.noteschallenge4.repositories

import androidx.annotation.WorkerThread
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.db.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class NotesRepository(private val notesDao: NotesDao) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    // return getNotesByUser as a Flow
    // so can be collected with collect()
    fun getNotesByUser(email: String) : Flow<List<Notes>> {
        return notesDao.getNotesByUser(email)
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