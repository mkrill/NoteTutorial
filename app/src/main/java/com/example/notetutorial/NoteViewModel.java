package com.example.notetutorial;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<NoteWithCategory>> allNotesWithCategory;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allNotesWithCategory = repository.getAllNotesWithCategory();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {

        repository.update(note);
    }

    public void delete(Note note) {

        repository.delete(note);
    }

    public void deleteNoteWithId(long id) {

        repository.deleteNoteWithId(id);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteWithCategory>> getAllNotesWithCategory() {
        return allNotesWithCategory;
    }

}
