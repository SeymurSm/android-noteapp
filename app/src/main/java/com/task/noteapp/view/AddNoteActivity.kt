package com.task.noteapp.view

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.R
import com.task.noteapp.api.DatabaseHandler
import com.task.noteapp.data.MainRepository
import com.task.noteapp.data.Note
import com.task.noteapp.utils.AddNoteViewModelFactory
import com.task.noteapp.viewmodels.AddNoteViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity(), View.OnClickListener  {
    /**
     * An variable to get an instance calendar using the default time zone and locale.
     */
    lateinit var viewModel: AddNoteViewModel
    private var mNoteDetails: Note? = null
    private var editing:Boolean = false
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // This is to use the home back button.

        if (intent.hasExtra(MainActivity.EXTRA_NOTE_DETAILS)) {
            mNoteDetails =
                intent.getSerializableExtra(MainActivity.EXTRA_NOTE_DETAILS) as Note
        }

        if (mNoteDetails != null) {
            editing = true
            supportActionBar?.title = "Edit Note"
            et_title.setText(mNoteDetails!!.title)
            et_description.setText(mNoteDetails!!.description)
            et_image_url.setText(mNoteDetails!!.imageUrl)
            btn_save.text = "UPDATE"
        }

        btn_save.setOnClickListener(this)

        viewModel =
            ViewModelProvider(this, AddNoteViewModelFactory(MainRepository(DatabaseHandler(this)))).get(
                AddNoteViewModel::class.java
            )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_save -> {
                when {
                    et_title.text.isNullOrEmpty() -> {
                        til_title.isErrorEnabled = true
                        til_title.error = getString(R.string.error_add_title)
                    }
                    et_description.text.isNullOrEmpty() -> {
                        til_description.isErrorEnabled = true
                        til_description.error = getString(R.string.error_add_description)
                    }
                    else -> {
                        val sdf = SimpleDateFormat("dd/M/yyyy")
                        val currentDate = sdf.format(Date())
                        // Assigning all the values to data model class.
                        val note = Note(
                            if (mNoteDetails == null) 0 else mNoteDetails!!.id,
                            et_title.text.toString(),
                            et_description.text.toString(),
                            et_image_url.text.toString(),
                            currentDate,
                            if (editing) "edited" else ""
                        )

                        // Here we initialize the database handler class.
                        if (mNoteDetails == null) {
                            val result = viewModel.addNote(note)

                            if (result > 0) {
                                setResult(Activity.RESULT_OK)
                                finish()//finishing activity
                            }
                        } else {
                            val result = viewModel.updateNote(note)

                            if (result > 0) {
                                setResult(Activity.RESULT_OK)
                                finish()//finishing activity
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}