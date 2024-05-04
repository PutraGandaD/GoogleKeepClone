package com.putragandad.noteschallenge4.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.putragandad.noteschallenge4.utils.Constant
import com.putragandad.noteschallenge4.utils.SharedPreferencesManager

class UserViewModel : ViewModel() {
    fun login(email: String, password: String) : Boolean {
        val emailFromSp = SharedPreferencesManager.getString(Constant.USER_EMAIL, "")
        val passwordFromSp = SharedPreferencesManager.getString(Constant.USER_PASSWORD, "")

        val loginAuth = if(email == emailFromSp && password == passwordFromSp) {
            SharedPreferencesManager.putBoolean(Constant.LOGIN_STATUS, true)
            true
        } else {
            false
        }
        return loginAuth
    }

    fun register(email: String, name: String, password: String, passwordCv: String) : Boolean {
        val registerAuth = if(password == passwordCv) {
            SharedPreferencesManager.putString(Constant.USER_EMAIL, email)
            SharedPreferencesManager.putString(Constant.USER_NAME, name)
            SharedPreferencesManager.putString(Constant.USER_PASSWORD, password)
            true
        } else {
            false
        }
        return registerAuth
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