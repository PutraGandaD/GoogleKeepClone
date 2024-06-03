package com.putragandad.noteschallenge4.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.repositories.NotesRepository
import com.putragandad.noteschallenge4.utils.Constant
import com.putragandad.noteschallenge4.utils.SharedPreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    private var _userEmail : String = ""
    val userEmail: String
        get() = _userEmail

    private val _notesByUser = MutableLiveData<List<Notes>>()
    val notesByUser : LiveData<List<Notes>>
        get() = _notesByUser

    fun setUserEmail(value: String) {
        _userEmail = value
        updateNotesData()
    }

    // launch coroutine for executing repository function

    // collect from Flow in the repository
    private fun updateNotesData() {
        viewModelScope.launch {
            repository.getNotesByUser(userEmail).collect{
                _notesByUser.value = it
            }
        }
    }

    fun searchNotesByUser(query: String) : LiveData<List<Notes>> {
        return repository.searchNotes(userEmail, query).asLiveData()
    }

    fun insert(notes: Notes) = viewModelScope.launch {
        repository.insert(notes)
    }

    fun update(notes: Notes) = viewModelScope.launch {
        repository.update(notes)
    }

    fun delete(notes: Notes) = viewModelScope.launch {
        repository.delete(notes)
    }
}

// This class below created the ViewModel above
class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}