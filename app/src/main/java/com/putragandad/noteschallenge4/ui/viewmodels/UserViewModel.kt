package com.putragandad.noteschallenge4.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.putragandad.noteschallenge4.utils.Constant
import com.putragandad.noteschallenge4.utils.SharedPreferencesManager

class UserViewModel : ViewModel() {
    fun login() {
        SharedPreferencesManager.putBoolean(Constant.LOGIN_STATUS, true)
    }

    fun register(email: String, name: String) {
        SharedPreferencesManager.putString(Constant.USER_EMAIL, email)
        SharedPreferencesManager.putString(Constant.USER_NAME, name)
    }

    fun checkLogin() : Boolean
    {
        return SharedPreferencesManager.getBoolean(Constant.LOGIN_STATUS, false)
    }

    fun getUserName() : String {
        return SharedPreferencesManager.getString(Constant.USER_NAME, "")
    }

    fun getEmail() : String {
        return SharedPreferencesManager.getString(Constant.USER_EMAIL, "")
    }

    fun logout() {
        SharedPreferencesManager.putBoolean(Constant.LOGIN_STATUS, false)
    }
}