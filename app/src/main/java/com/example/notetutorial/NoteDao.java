package com.example.notetutorial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note node);

    @Query("DELETE FROM note_table WHERE note_id = :id")
    void deleteNoteWithId(Long id);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("Select n.*, c.category_id as c_category_id, c.name as c_name from note_table n LEFT JOIN category_table c ON n.fk_category_id = c.category_id ORDER BY priority DESC")
    LiveData<List<NoteWithCategory>> getAllNotesWithCategory();

}
