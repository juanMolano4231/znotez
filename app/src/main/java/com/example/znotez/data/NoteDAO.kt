package com.example.znotez.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.znotez.data.model.Note
import com.example.znotez.data.model.NoteWithAttachments
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteWithAttachments(noteId: Long): Flow<NoteWithAttachments?>

    @Insert
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert
    suspend fun insertAttachment(attachment: com.example.znotez.data.model.Attachment)
}