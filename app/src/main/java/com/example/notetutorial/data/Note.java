package com.example.notetutorial.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(tableName = "note_table",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "fk_category_id",
                onDelete = ForeignKey.NO_ACTION))
public class Note {

    @Setter
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private long id;

    private String title;
    private String description;
    private int priority;

    @Setter
    @ColumnInfo(name = "fk_category_id")
    private long categoryId;

    public Note(String title, String description, int priority, long categoryId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.categoryId = categoryId;
    }

}
