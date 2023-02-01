package com.task.noteapp.viewmodels

import androidx.lifecycle.ViewModel
import com.task.noteapp.data.MainRepository
import com.task.noteapp.data.Note

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    fun getNotesList(): ArrayList<Note> {
        return repository.getNotesList()
    }
}