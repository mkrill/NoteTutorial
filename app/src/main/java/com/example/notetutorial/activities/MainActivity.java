package com.example.notetutorial.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notetutorial.viewmodels.NoteViewModel;
import com.example.notetutorial.R;
import com.example.notetutorial.adapters.NoteWithCategoryAdapter;
import com.example.notetutorial.data.Note;
import com.example.notetutorial.data.NoteWithCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

   private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteWithCategoryAdapter noteWithCategoryAdapter = new NoteWithCategoryAdapter();
        recyclerView.setAdapter(noteWithCategoryAdapter);

        noteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(NoteViewModel.class);
        noteViewModel.getAllNotesWithCategory().observe(this, new Observer<List<NoteWithCategory>>() {
            @Override
            public void onChanged(@Nullable List<NoteWithCategory> notesWithCategory) {
                // update RecyclerView
                noteWithCategoryAdapter.submitList(notesWithCategory);
            }
        });

        // Allow swipes to left and right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                NoteWithCategory noteWithCategory = noteWithCategoryAdapter.getNoteWithCategoryAt(viewHolder.getAdapterPosition());
                noteViewModel.deleteNoteWithId(noteWithCategory.node.getId());
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }
        ).attachToRecyclerView(recyclerView);

        noteWithCategoryAdapter.setOnItemClickListener(new NoteWithCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteWithCategory noteWithCategory) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, noteWithCategory.node.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, noteWithCategory.node.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, noteWithCategory.node.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, noteWithCategory.node.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_CATID, noteWithCategory.node.getCategoryId());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            int categoryId = data.getIntExtra(AddEditNoteActivity.EXTRA_CATID, 1);

            Note note = new Note(title, description, priority, categoryId);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            long id = data.getLongExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Not cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);

                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
                long categoryId = data.getLongExtra(AddEditNoteActivity.EXTRA_CATID, 1);

                Note note = new Note(title, description, priority, categoryId);
                note.setId(id);

                noteViewModel.update(note);
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

            }
        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
