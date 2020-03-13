package com.example.notetutorial.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM category_table WHERE category_id = :id")
    Category findCategoryById(long id);

    @Query("DELETE FROM category_table")
    void deleteAllCategories();

    @Query("SELECT * FROM category_table ORDER BY name ASC")
    LiveData<List<Category>> getAllCategories();

    @Transaction
    @Query("SELECT * FROM category_table ORDER BY name ASC")
    LiveData<List<CategoryWithNotes>> getCategoryWithNotes();

}
