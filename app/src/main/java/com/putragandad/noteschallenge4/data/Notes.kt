package com.putragandad.noteschallenge4.data

import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Notes(
    val id: Int,
    val title: String,
    val notesContent: String,
    val userEmail: String,
)
