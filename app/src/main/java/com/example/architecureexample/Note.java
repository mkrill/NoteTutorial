package com.example.architecureexample;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "note_table")
@Getter
@Setter
public class Note {

    @PrimaryKey(autoGenerate=true)
    private int id;

    private String title;
    private String description;

    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

}
