package com.example.notetutorial;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(tableName = "note_table")
public class Note {

    @Setter
    @PrimaryKey(autoGenerate=true)
    private int noteId;

    private String title;
    private String description;
    private int priority;

    @Setter
    private int categoryId;

    public Note(String title, String description, int priority, int categoryId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.categoryId = categoryId;
    }

}
