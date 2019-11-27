package com.example.note.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: note)

    @Query("SELECT * FROM note ORDER BY id DESC")
 suspend   fun  getAllNotes():List<note>

    @Insert
   suspend fun addMultipleNote(vararg note: note)

    @Update
    suspend fun updateNote(note : note)

    @Delete
    suspend fun deleteNote(note: note)


}