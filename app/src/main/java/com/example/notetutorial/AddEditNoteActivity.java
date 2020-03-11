package com.example.notetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String LOG_TAG = AddEditNoteActivity.class.getSimpleName();

    public static final String EXTRA_ID =
            "com.example.notetutorial.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.notetutorial.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.notetutorial.EXTRA_DECRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.notetutorial.EXTRA_PRIORITY";
    public static final String EXTRA_CATID =
            "com.example.notetutorial.EXTRA_CATID";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        spinnerCategory = findViewById(R.id.spinner_category);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            // ToDo initialize category
        } else {
            setTitle("Add Note");
        }

         CategoryViewModel categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                // update spinner after livedata changed
                ArrayAdapter<Category> catAdapter = new ArrayAdapter<Category>(AddEditNoteActivity.this, android.R.layout.simple_spinner_item, categories);
                catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(catAdapter);
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          // An item was selected. You can retrieve the selected item using
                                                          // parent.getItemAtPosition(pos)
                                                          // parent is the spinner itself!!!

                                                          Log.d(LOG_TAG, "Item " + parent.getItemAtPosition(position) + " was selected");

                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {
                                                          // Another interface callback
                                                          // parent is the spinner itself!!!
                                                          Log.d(LOG_TAG, "Nothing was selected");

                                                      }

                                                  }
        );

    }

    public void getSelectedCategory(View v) {
        Category category = (Category) spinnerCategory.getSelectedItem();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryId = selectedCategory.getCategoryId();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_CATID, categoryId);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

}
