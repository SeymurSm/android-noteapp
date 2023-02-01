package com.task.noteapp.data

import java.io.Serializable

/**
 * A Data Model Class for Note details. We will you this data class in all over the project even when
 * dealing with local SQLite database.
 */
data class Note(
    val id: Int,
    val title: String,
    val imageUrl: String,
    var description: String,
    val date: String,
    val editStatus: String
) : Serializable