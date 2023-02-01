package com.task.noteapp.data

import com.task.noteapp.api.DatabaseHandler

class MainRepository constructor(private val databaseHandler: DatabaseHandler) {
    fun addNote(note: Note): Long  { return databaseHandler.addNote(note)}
    fun updateNote(note: Note) : Int { return databaseHandler.updateNote(note)}
    fun getNotesList() : ArrayList<Note> { return databaseHandler.getNotesList()}
}