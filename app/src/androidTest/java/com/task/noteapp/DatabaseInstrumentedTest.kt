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
            var note = Note(2, "Title", "Description", "image_url", currentDate, "")
            mDataSource!!.addNote(note)
            val notesFinal: List<Note> = mDataSource!!.getNotesList()
            assertThat(notesFinal.size, `is`(1))
            assertEquals(notesFinal[0].title, note.title)
            println(notesFinal[0].description + "DESCRIPTIOJJM")
            assertEquals(notesFinal[0].description, note.description)
        }
    }
}