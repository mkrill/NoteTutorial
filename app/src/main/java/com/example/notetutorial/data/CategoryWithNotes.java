package com.example.notetutorial.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.notetutorial.data.Category;
import com.example.notetutorial.data.Note;

import java.util.List;

public class CategoryWithNotes {

    @Embedded
    public Category category;

    @Relation(parentColumn = "category_id", entityColumn = "fk_category_id")
    public List<Note> notes;
}
