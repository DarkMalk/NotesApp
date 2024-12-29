package services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import models.Note;
import utils.DatabaseHelper;

public class NotesServices {
    private final DatabaseHelper databaseHelper;
    public NotesServices(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addNote(Note note) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.TABLE_TITLE, note.getTitle());
        content.put(DatabaseHelper.TABLE_DESCRIPTION, note.getDescription());

        long id = db.insert(DatabaseHelper.TABLE_NAME, null, content);

        db.close();

        return id;
    }

    public ArrayList<Note> getNotes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        ArrayList<Note> notes = new ArrayList<>();

        String[] columns = new String[] {
                DatabaseHelper.TABLE_ID,
                DatabaseHelper.TABLE_TITLE,
                DatabaseHelper.TABLE_DESCRIPTION,
                DatabaseHelper.TABLE_CREATED_AT
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );


        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TABLE_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TABLE_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TABLE_DESCRIPTION));
                long created_at = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.TABLE_CREATED_AT));

                Date date = new Date(created_at * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateFormat = sdf.format(date);

                Note newNote = new Note(id, title, description, dateFormat);

                notes.add(newNote);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return notes;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.TABLE_TITLE, note.getTitle());
        content.put(DatabaseHelper.TABLE_DESCRIPTION, note.getDescription());

        String whereClause = DatabaseHelper.TABLE_ID + " = ?";
        String[] whereArgs = new String[] { String.valueOf(note.getId()) };

        int rowAffected = db.update(DatabaseHelper.TABLE_NAME, content, whereClause, whereArgs);

        db.close();

        return rowAffected;
    }

    public int deleteNote(Note note) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String whereClause = DatabaseHelper.TABLE_ID + " = ?";
        String[] whereArgs = new String[] { String.valueOf(note.getId()) };

        int rowAffected = db.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);

        db.close();

        return rowAffected;
    }

}
