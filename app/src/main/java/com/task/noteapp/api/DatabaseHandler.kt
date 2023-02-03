package com.task.noteapp.api

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.task.noteapp.data.Note

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Database version
        const val DATABASE_NAME = "NoteDatabase" // Database name
        private const val TABLE_NOTE = "NotesTable" // Table Name

        //All the Columns names
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE_URL = "image"
        private const val KEY_DATE = "date"
        private const val KEY_EDIT_STATUS = "edited"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_NOTE_TABLE = ("CREATE TABLE " + TABLE_NOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_EDIT_STATUS + " INTEGER)"
                )
        db?.execSQL(CREATE_NOTE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE")
        onCreate(db)
    }

    /**
     * Function to insert a Note details to SQLite Database.
     */
    fun addNote(note: Note): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ID, note.id) // NoteModelClass TITLE
        contentValues.put(KEY_TITLE, note.title) // NoteModelClass TITLE
        contentValues.put(KEY_DESCRIPTION, note.description) // NoteModelClass DESCRIPTION
        contentValues.put(KEY_IMAGE_URL, note.imageUrl) // NoteModelClass IMAGE
        contentValues.put(KEY_DATE, note.date) // NoteModelClass DATE
        contentValues.put(KEY_EDIT_STATUS, 0) // NoteModelClass DATE

        // Inserting Row
        val result = db.insert(TABLE_NOTE, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return result
    }

    /**
     * Function to read all the list of Notes data which are inserted.
     */
    fun getNotesList(): ArrayList<Note> {

        // A list is initialize using the data model class in which we will add the values from cursor.
        val noteList: ArrayList<Note> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_NOTE" // Database select query

        val db = this.writableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = Note(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_EDIT_STATUS))
                    )
                    noteList.add(place)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return noteList
    }

    /**
     * Function to update record
     */
    fun updateNote(note: Note): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, note.title) // NoteModelClass TITLE
        contentValues.put(KEY_DESCRIPTION, note.description) // NoteModelClass DESCRIPTION
        contentValues.put(KEY_IMAGE_URL, note.imageUrl) // NoteModelClass IMAGE
        contentValues.put(KEY_DATE, note.date) // NoteModelClass DATE
        contentValues.put(KEY_EDIT_STATUS, 1) // NoteModelClass DATE

        // Updating Row
        val success =
            db.update(TABLE_NOTE, contentValues, KEY_ID + "=" + note.id, null)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    /**
     * Function to delete note details.
     */
    fun deleteNote(note: Note): Int {
        val db = this.writableDatabase
        // Deleting Row
        val success = db.delete(TABLE_NOTE, KEY_ID + "=" + note.id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}