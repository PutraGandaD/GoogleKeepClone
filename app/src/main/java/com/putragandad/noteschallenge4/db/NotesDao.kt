package com.putragandad.noteschallenge4.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.putragandad.noteschallenge4.data.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    // Getting all words based on user email
    @Query("SELECT * FROM note_table WHERE userEmail = :email ORDER BY notesContent ASC")
    fun getNotesByUser(email: String) : Flow<List<Notes>> // Use Flow

    // Inserting a notes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Notes)

    // Update a notes
    @Update
    suspend fun update(notes: Notes)

    // Delete a notes
    @Delete
    suspend fun delete(notes: Notes)
}