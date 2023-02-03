package com.task.noteapp

import android.content.Context
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.task.noteapp.api.DatabaseHandler
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class DatabaseInstrumentedTest {
    @RunWith(AndroidJUnit4::class)
    @LargeTest
    internal class DatabaseInstrumentedTest {
        private var mDataSource: DatabaseHandler? = null
        lateinit var instrumentationContext: Context

        @Before
        fun setUp() {
            getTargetContext().deleteDatabase(DatabaseHandler.DATABASE_NAME)
            instrumentationContext = InstrumentationRegistry.getInstrumentation().context
            mDataSource = DatabaseHandler(instrumentationContext)
        }

        @After
        fun finish() {
            mDataSource!!.close()
        }

        @Test
        fun testPreConditions() {
            assertNotNull(mDataSource)
        }
    }
}