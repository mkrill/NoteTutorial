package com.example.notetutorial.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class, Category.class}, version = 3)
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

    // Erzeugung eines statischen NoteDatabase-Attributs vom Typ Callback (definiert in RoomDatabase-Klasse) inklusive dessen Initialisierung
    // new RoomDatabase.Callback() {} erzeugt ein Objekt, einer von RoomDatabase.Callback() abgeleiteten Klasse, deren onCreate()-Methode
    // direkt überschrieben wird
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // Man braucht keine Instanz von NoteDatabase, um ein Objekt von Populate... zu erzeugen
    // 3* Void => keine Paramater, kein Progress, kein Result
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
            long category_id = categoryDao.insert(catHousehold);
            catHousehold.setId(category_id);

            Category catSport = new Category("Sport");
            category_id = categoryDao.insert(catSport);
            catSport.setId(category_id);

            Category catGardening = new Category("Gardening");
            category_id = categoryDao.insert(catGardening);
            catGardening.setId(category_id);

            Category catWork = new Category("Work");
            category_id = categoryDao.insert(catWork);
            catWork.setId(category_id);

            // Populate initial notes with categories
            noteDao.insert(new Note("Title 1", "Description 1", 1, catHousehold.getId()));
            noteDao.insert(new Note("Title 2", "Description 2", 2, catWork.getId()));
            noteDao.insert(new Note("Title 3", "Description 3", 3, catGardening.getId()));

            return null;
        }
    }

}
