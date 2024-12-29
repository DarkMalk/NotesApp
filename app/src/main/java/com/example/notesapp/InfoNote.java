package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import models.Note;
import services.NotesServices;
import utils.Dialog;

public class InfoNote extends AppCompatActivity {
    private TextInputEditText etTitle, etDescription, etDate;
    private ImageButton btnBackToolbar, btnDeleteToolbar;
    private NotesServices notesServices;
    private Button btnEditNote;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_note);
        notesServices = new NotesServices(this);

        initializeElements();
        getData();
        setDataInInputs();

        btnEditNote.setOnClickListener((view) -> submit());
        btnBackToolbar.setOnClickListener((view) -> finish());
        btnDeleteToolbar.setOnClickListener((view) -> confirmDeleteNote());
    }

    private void initializeElements() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        btnEditNote = findViewById(R.id.btnEditNote);
        btnBackToolbar = findViewById(R.id.btnBackToolbar);
        btnDeleteToolbar = findViewById(R.id.btnDeleteToolbar);
    }

    private void getData() {
        Intent i = getIntent();
        note = (Note) i.getSerializableExtra("note");
    }

    private void setDataInInputs() {
        etTitle.setText(note.getTitle());
        etDescription.setText(note.getDescription());
        etDate.setText(note.getCreatedAt());
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

    private void submit() {
        String title = String.valueOf(etTitle.getText());
        String description = String.valueOf(etDescription.getText());

        boolean isValid = validateInputs(title, description);

        if (!isValid) {
            Toast.makeText(
                    this,
                    getString(R.string.toast_error_note_not_updated),
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        note.setTitle(title);
        note.setDescription(description);

        Dialog.showDialog(
                this,
                getString(R.string.edit),
                getString(R.string.dialog_desc_update_note),
                (isConfirm) -> {
                    if (!isConfirm) {
                        Toast.makeText(
                                this,
                                getString(R.string.toast_error_note_not_updated),
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                        return;
                    }

                    notesServices.updateNote(note);
                    Toast.makeText(
                            this,
                            getString(R.string.toast_success_updated),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }

    private void confirmDeleteNote() {
        Dialog.showDialog(
                this,
                getString(R.string.delete),
                getString(R.string.dialog_desc_delete_note),
                this::deleteNote
        );
    }

    private void deleteNote(boolean isConfirm) {
        if (!isConfirm) {
            Toast.makeText(
                    this,
                    getString(R.string.toast_cancel_delete_note),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        notesServices.deleteNote(note);

        Toast.makeText(
                this,
                getString(R.string.toast_delete_note),
                Toast.LENGTH_LONG
        ).show();

        finish();
    }
}