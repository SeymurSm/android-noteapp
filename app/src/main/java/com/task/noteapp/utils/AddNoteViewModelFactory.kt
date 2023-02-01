package com.task.noteapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.data.MainRepository
import com.task.noteapp.view.AddNoteActivity
import com.task.noteapp.viewmodels.AddNoteViewModel

class AddNoteViewModelFactory constructor(private val repository: MainRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            AddNoteViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}