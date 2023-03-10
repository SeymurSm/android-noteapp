package com.task.noteapp

import android.content.Context
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.task.noteapp.api.DatabaseHandler
import com.task.noteapp.data.Note
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

class DatabaseInstrumentedTest {
    @RunWith(AndroidJUnit4::class)
    @LargeTest
    internal class DatabaseInstrumentedTest {
        private var mDataSource: DatabaseHandler? = null
        lateinit var targetContext: Context

        @Before
        fun setUp() {
            getTargetContext().deleteDatabase(DatabaseHandler.DATABASE_NAME)
            targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            mDataSource = DatabaseHandler(targetContext)
        }

        @After
        fun finish() {
            mDataSource!!.close()
        }

        @Test
        fun testPreConditions() {
            assertNotNull(mDataSource)
        }

        @Test
        @Throws(Exception::class)
        fun testAddNote() {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())
            var note = Note(0, "Title", "Description", "image_url", currentDate, 0)
            mDataSource!!.addNote(note)
            val notesFinal: List<Note> = mDataSource!!.getNotesList()
            assertThat(notesFinal.size, `is`(1))
            assertEquals(notesFinal[0].title, note.title)
            assertEquals(notesFinal[0].description, note.description)
        }

        @Test
        fun testDeleteNote() {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())
            var note = Note(0, "Title", "Description", "image_url", currentDate, 0)
            mDataSource!!.addNote(note)
            note = mDataSource!!.getNotesList()[0]
            mDataSource!!.deleteNote(note)
            val notes: List<Note> = mDataSource!!.getNotesList()
            assertThat(notes.size, `is`(0))
        }

        @Test
        fun testUpdateNote() {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())
            var note = Note(0, "Title", "Description", "image_url", currentDate, 0)
            mDataSource!!.addNote(note)
            note = mDataSource!!.getNotesList()[0]
            note.description = "Description_updated"
            mDataSource!!.updateNote(note)
            val noteList = mDataSource!!.getNotesList()
            assertEquals(noteList[noteList.size - 1].description, "Description_updated")
        }

        @Test
        fun testEditStatus() {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())
            var note = Note(0, "Title", "Description", "image_url", currentDate, 0)
            mDataSource!!.addNote(note)
            note = mDataSource!!.getNotesList()[0]
            note.description = "Updated description"
            mDataSource!!.updateNote(note)
            assertThat(mDataSource!!.getNotesList()[0].editStatus, `is`(1))
        }

        @Test
        fun testAddAndDelete() {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())
            var note = Note(0, "Title", "Description", "image_url", currentDate, 0)
            mDataSource!!.addNote(note)
            note = mDataSource!!.getNotesList()[0]
            mDataSource!!.deleteNote(note)
            assertThat(mDataSource!!.getNotesList().size, `is`(0))
        }
    }
}