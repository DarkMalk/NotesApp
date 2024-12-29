package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import models.Note;
import services.NotesServices;

public class MainActivity extends AppCompatActivity {
    private NotesServices notesServices;
    private FloatingActionButton btnAddNote;
    private ListView listViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private ArrayAdapter<Note> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesServices = new NotesServices(this);

        getData();

        initializeElements();
        configListView();

        btnAddNote.setOnClickListener((view) -> navigateToAddNote());
        onClickItemListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
        adapter.notifyDataSetChanged();
    }

    private void initializeElements() {
        btnAddNote = findViewById(R.id.btnAddNote);
        listViewNotes = findViewById(R.id.listViewNotes);
    }

    private void configListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listViewNotes.setAdapter(adapter);
    }

    private void navigateToAddNote() {
        startActivity(new Intent(this, newNote.class));
    }

    private void getData() {
        notes.clear();
        notes.addAll(notesServices.getNotes());
    }

    private void onClickItemListView() {
        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), InfoNote.class);
                Note note = notes.get(position);
                i.putExtra("note", note);
                startActivity(i);
            }
        });
    }
}