package com.task.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.task.noteapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting an click event for Fab Button and calling the AddNoteActivity.
        fabAddNewNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_ACTIVITY_REQUEST_CODE)
        }
    }

    companion object {
        private const val ADD_NOTE_ACTIVITY_REQUEST_CODE = 1
        internal const val EXTRA_NOTE_DETAILS = "extra_note_details"
    }
}