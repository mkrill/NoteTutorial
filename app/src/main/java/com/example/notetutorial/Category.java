package com.example.notetutorial;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(tableName = "category_table")
public class Category {

    @Setter
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "category_id")
    private long id;

    private String name;

    public Category(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
