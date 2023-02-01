package com.task.noteapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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

        var noteDetailModel: Note? = null

        if (intent.hasExtra(MainActivity.EXTRA_NOTE_DETAILS)) {
            // get the Serializable data model class with the details in it
            noteDetailModel =
                intent.getSerializableExtra(MainActivity.EXTRA_NOTE_DETAILS) as Note
        }

        if (noteDetailModel != null) {

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = noteDetailModel.title

            if (!noteDetailModel.imageUrl.isNullOrEmpty()) {
                iv_note_image.visibility = View.VISIBLE
                tv_img_url.text = noteDetailModel.imageUrl
                Picasso.get()
                    .load(noteDetailModel.imageUrl)
                    .resize(300, 300)
                    .centerCrop()
                    .into(iv_note_image)
            }
            tv_description.text = noteDetailModel.description
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