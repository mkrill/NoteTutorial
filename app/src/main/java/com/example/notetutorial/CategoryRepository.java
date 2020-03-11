package com.example.notetutorial;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<CategoryWithNotes>> categoryWithNotes;

    public CategoryRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        categoryDao = database.getCategoryDao();
        allCategories = categoryDao.getAllCategories();
        categoryWithNotes = categoryDao.getCategoryWithNotes();

    }

    public void insert(Category category) {
        new InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    public void update(Category category) {

        new UpdateCategoryAsyncTask(categoryDao).execute(category);
    }
    public void delete(Category category) {

        new DeleteCategoryAsyncTask(categoryDao).execute(category);
    }

    public void deleteAllCategories() {

        new DeleteAllCategoriesAsyncTask(categoryDao).execute();
    }

    public LiveData<List<Category>> getAllCategories() {

        return allCategories;
    }

    public LiveData<List<CategoryWithNotes>> getCategoryWithNotes() {

        return categoryWithNotes;
    }

    // static necessary, because it needs to be instantiated before the outer object is instantiated
    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        private InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        private UpdateCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.update(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        private DeleteCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.delete(categories[0]);
            return null;
        }
    }

    private static class DeleteAllCategoriesAsyncTask extends AsyncTask<Void, Void, Void> {

        private CategoryDao categoryDao;

        private DeleteAllCategoriesAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.deleteAllCategories();
            return null;
        }
    }
}
