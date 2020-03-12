package com.example.notetutorial;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithNotes {

    @Embedded
    public Category category;

    @Relation(parentColumn = "category_id", entityColumn = "fk_category_id")
    public List<Note> notes;
}
