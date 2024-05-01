package com.putragandad.noteschallenge4

import android.app.Application
import com.putragandad.noteschallenge4.utils.SharedPreferencesManager

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}