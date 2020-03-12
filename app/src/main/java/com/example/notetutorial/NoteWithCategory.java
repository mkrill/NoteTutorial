package com.example.notetutorial;

import androidx.room.Embedded;

public class NoteWithCategory {

    @Embedded
    Note node;

    @Embedded(prefix = "c_")
    Category category;
}
