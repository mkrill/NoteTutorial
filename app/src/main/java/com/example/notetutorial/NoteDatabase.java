package com.example.notetutorial;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class, Category.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao getNoteDao();
    public abstract CategoryDao getCategoryDao();

    public static synchronized NoteDatabase getInstance(Context context) {
     if (instance == null) {
         instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                 .fallbackToDestructiveMigration()
                 .addCallback(roomCallback)
                 .build();
     }
     return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(NoteDatabase db) {

            noteDao = db.getNoteDao();
            categoryDao = db.getCategoryDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Populate initial categories
            Category catHousehold = new Category("Household");
            categoryDao.insert(catHousehold);
            Category catSport = new Category("Sport");
            categoryDao.insert(catSport);
            Category catGardening = new Category("Gardening");
            categoryDao.insert(catGardening);
            Category catWork = new Category("Work");
            categoryDao.insert(catWork);

            // Populate initial notes with categories
            noteDao.insert(new Note("Title 1", "Description 1", 1, catHousehold.getCategoryId()));
            noteDao.insert(new Note("Title 2", "Description 2", 2, catWork.getCategoryId()));
            noteDao.insert(new Note("Title 3", "Description 3", 3, catGardening.getCategoryId()));

            return null;
        }
    }

}
