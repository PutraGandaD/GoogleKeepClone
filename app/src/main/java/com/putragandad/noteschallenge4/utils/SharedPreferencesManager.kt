package com.putragandad.noteschallenge4.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String) : String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean) : Boolean {
        return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
    }
}