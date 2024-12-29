package com.example.notesapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import models.Note;
import services.NotesServices;

public class newNote extends AppCompatActivity {
    private TextInputEditText etTitle, etDescription;
    private ImageButton btnBackToolbar;
    private NotesServices notesServices;
    private MaterialButton btnNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        notesServices = new NotesServices(this);

        initializeElements();

        btnNewNote.setOnClickListener(view -> submit());
        btnBackToolbar.setOnClickListener(view -> finish());
    }

    private void submit() {
        String title = String.valueOf(etTitle.getText());
        String description = String.valueOf(etDescription.getText());

        boolean isValid = validateInputs(title, description);

        if (!isValid) {
            Toast.makeText(
                    this,
                    getString(R.string.toast_error_note_not_created),
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        Note note = new Note(title, description);
        notesServices.addNote(note);

        Toast.makeText(
                this,
                getString(R.string.toast_success_created_note),
                Toast.LENGTH_LONG
        ).show();

        finish();
    }

    private void initializeElements() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnNewNote = findViewById(R.id.btnNewNote);
        btnBackToolbar = findViewById(R.id.btnBackToolbar);
    }

    private boolean validateInputs(String title, String description) {
        if (title.trim().isBlank()) {
            etTitle.setError(getString(R.string.et_error_input_blank));
            return false;
        }

        if (description.trim().isBlank()) {
            etDescription.setError(getString(R.string.et_error_input_blank));
            return false;
        }

        return true;
    }
}