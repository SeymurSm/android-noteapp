package com.task.noteapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.noteapp.data.MainRepository
import com.task.noteapp.data.Note

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val noteList = MutableLiveData<ArrayList<Note>>()

    fun getNotesList() {
        noteList.postValue(repository.getNotesList())
    }

    fun deleteNote(note: Note): Int {
        return repository.deleteNote(note)
    }
}