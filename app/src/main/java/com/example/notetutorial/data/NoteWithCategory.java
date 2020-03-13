package com.example.notetutorial.data;

import androidx.room.Embedded;

public class NoteWithCategory {

    @Embedded
    public Note node;

    @Embedded(prefix = "c_")
    public Category category;
}
