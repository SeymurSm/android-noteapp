package com.task.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.adapters.NotesAdapter
import com.task.noteapp.api.DatabaseHandler
import com.task.noteapp.data.MainRepository
import com.task.noteapp.data.Note
import com.task.noteapp.utils.MainViewModelFactory
import com.task.noteapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting an click event for Fab Button and calling the AddNoteActivity.
        fabAddNewNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_ACTIVITY_REQUEST_CODE)
        }

        setupUI()
    }

    private fun setupUI(){
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(DatabaseHandler(this)))).get(
                MainViewModel::class.java
            )
        setUpNotesList()
    }

    /**
     * A function to get the list of note from local database.
     */
    private fun setUpNotesList() {

        val notesList = viewModel.getNotesList()

        if (notesList.size > 0) {
            rv_notes_list.visibility = View.VISIBLE
            tv_no_records_available.visibility = View.GONE
            setupNotesRecyclerView(notesList)
        } else {
            rv_notes_list.visibility = View.GONE
            tv_no_records_available.visibility = View.VISIBLE
        }
    }

    /**
     * A function to populate the recyclerview to the UI.
     */
    private fun setupNotesRecyclerView(noteList: ArrayList<Note>) {
        rv_notes_list.layoutManager = LinearLayoutManager(this)
        rv_notes_list.setHasFixedSize(true)

        val placesAdapter = NotesAdapter(this, noteList)
        rv_notes_list.adapter = placesAdapter


    }


    companion object {
        private const val ADD_NOTE_ACTIVITY_REQUEST_CODE = 1
        internal const val EXTRA_NOTE_DETAILS = "extra_note_details"
    }
}