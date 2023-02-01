package com.task.noteapp.viewmodels

import androidx.lifecycle.ViewModel
import com.task.noteapp.api.DatabaseHandler
import com.task.noteapp.data.MainRepository
import com.task.noteapp.data.Note

class AddNoteViewModel(private val repository: MainRepository) : ViewModel()  {

    fun addNote(note: Note): Long {
        return repository.addNote(note)
    }

    fun updateNote(note: Note): Int {
        return repository.updateNote(note)
    }
}