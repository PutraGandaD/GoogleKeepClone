package com.putragandad.noteschallenge4.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.repositories.NotesRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val allNotes : LiveData<List<Notes>> = repository.allNotes.asLiveData()

    // launch coroutine for executing repository function
    fun getNotesByUser(email: String) = viewModelScope.launch {
        repository.getNotesByUser(email)
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