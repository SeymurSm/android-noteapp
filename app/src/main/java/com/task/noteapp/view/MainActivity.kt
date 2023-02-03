package com.task.noteapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
import com.task.noteapp.utils.SwipeToDeleteCallback
import com.task.noteapp.utils.SwipeToEditCallback
import com.task.noteapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var viewModel: MainViewModel
    private val WRITE_REQUEST_CODE = 333

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting an click event for Fab Button and calling the AddNoteActivity.
        fabAddNewNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_ACTIVITY_REQUEST_CODE)
        }

        setupUI()
        // Nedded for instrumented testing
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupPermissions()
        }
    }

    // Call Back method  to get the Message form other Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check if the request code is same as what is passed  here it is 'ADD_PLACE_ACTIVITY_REQUEST_CODE'
        if (requestCode == ADD_NOTE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                setUpNotesList()
            } else {
                Log.e(TAG, "Cancelled or Back Pressed")
            }
        }
    }

    private fun setupUI() {
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(MainRepository(DatabaseHandler(this)))
            ).get(
                MainViewModel::class.java
            )
        setUpNotesList()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupPermissions() {
        hasStoragePermission(WRITE_REQUEST_CODE)
    }

    /**
     * A function to get the list of note from local database.
     */
    private fun setUpNotesList() {

        viewModel.getNotesList()

        viewModel.noteList.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, "notesList: $it")
                rv_notes_list.visibility = View.VISIBLE
                tv_no_records_available.visibility = View.GONE
                setupNotesRecyclerView(viewModel.noteList.value!!)
            } else {
                rv_notes_list.visibility = View.GONE
                tv_no_records_available.visibility = View.VISIBLE
                Toast.makeText(
                    this,
                    getString(R.string.error_fetching_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun onDeleteClick(viewHolder: RecyclerView.ViewHolder, note: Note) {
        val alert: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alert.setTitle(getString(R.string.delete))
        alert.setMessage(getString(R.string.delete_title))
        alert.setPositiveButton(
            getString(R.string.yes),
            DialogInterface.OnClickListener { dialog, which ->
                val isDeleted = viewModel.deleteNote(note)

                if (isDeleted > 0) {
                    val adapter = rv_notes_list.adapter as NotesAdapter
                    adapter.removeAt(viewHolder.adapterPosition)
                    setUpNotesList()
                    dialog.dismiss()
                }
            })
        alert.setNegativeButton(getString(R.string.no),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                setUpNotesList()
            })
        alert.show()
    }

    /**
     * A function to populate the recyclerview to the UI.
     */
    private fun setupNotesRecyclerView(noteList: ArrayList<Note>) {
        rv_notes_list.layoutManager = LinearLayoutManager(this)
        rv_notes_list.setHasFixedSize(true)

        val notesAdapter = NotesAdapter(this, noteList)
        rv_notes_list.adapter = notesAdapter

        notesAdapter.setOnClickListener(object :
            NotesAdapter.OnClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(this@MainActivity, NoteDetailActivity::class.java)
                intent.putExtra(
                    EXTRA_NOTE_DETAILS,
                    noteList[position]
                ) // Passing the complete serializable data class to the detail activity using intent.
                startActivity(intent)
            }
        })


        val editSwipeHandler = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val intent = Intent(applicationContext, AddNoteActivity::class.java)
                intent.putExtra(
                    MainActivity.EXTRA_NOTE_DETAILS,
                    viewModel.noteList.value!![viewHolder.adapterPosition]
                )
                startActivityForResult(
                    intent,
                    ADD_NOTE_ACTIVITY_REQUEST_CODE
                ) // Activity is started with requestCode
                val adapter = rv_notes_list.adapter as NotesAdapter
                adapter.notifyEditItem(
                    viewHolder.adapterPosition,
                )
            }
        }
        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(rv_notes_list)

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onDeleteClick(viewHolder, viewModel.noteList.value!![viewHolder.adapterPosition])
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(rv_notes_list)
    }

    private fun hasStoragePermission(requestCode: Int): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
                false
            } else {
                true
            }
        } else {
            true
        }
    }


    companion object {
        private const val ADD_NOTE_ACTIVITY_REQUEST_CODE = 1
        internal const val EXTRA_NOTE_DETAILS = "extra_note_details"
    }
}