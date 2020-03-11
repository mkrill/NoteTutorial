package com.example.notetutorial;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithNotes {

    @Embedded
    public Category category;

    @Relation(parentColumn = "categoryId", entityColumn = "noteId")
    public List<Note> notes;
}
