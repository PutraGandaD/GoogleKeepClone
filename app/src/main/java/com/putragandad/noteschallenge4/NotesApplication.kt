package com.putragandad.noteschallenge4

import android.app.Application
import com.putragandad.noteschallenge4.db.NotesRoomDatabase
import com.putragandad.noteschallenge4.repositories.NotesRepository
import com.putragandad.noteschallenge4.utils.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    // initialize database and the repository when they're need only
    // inspired from Google Codelabs
    val database by lazy { NotesRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NotesRepository(database.notesDao()) }

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}