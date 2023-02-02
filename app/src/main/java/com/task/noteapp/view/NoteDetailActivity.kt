package com.task.noteapp.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.task.noteapp.R
import com.task.noteapp.data.Note
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_note_detail)

        var noteDetail: Note? = null

        if (intent.hasExtra(MainActivity.EXTRA_NOTE_DETAILS)) {
            // get the Serializable data model class with the details in it
            noteDetail =
                intent.getSerializableExtra(MainActivity.EXTRA_NOTE_DETAILS) as Note
        }

        if (noteDetail != null) {

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = noteDetail.title

            if (!noteDetail.imageUrl.isNullOrEmpty()) {
                iv_note_image.visibility = View.VISIBLE
                tv_img_url.text = noteDetail.imageUrl
                Picasso.get()
                    .load(noteDetail.imageUrl)
                    .resize(300, 300)
                    .centerCrop()
                    .into(iv_note_image)
            }
            tv_description.text = noteDetail.description
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