package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "note";
    public static final String TABLE_ID = "id";
    public static final String TABLE_TITLE = "title";
    public static final String TABLE_DESCRIPTION = "description";
    public static final String TABLE_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                TABLE_TITLE + " TEXT NOT NULL," +
                TABLE_DESCRIPTION + "  TEXT NOT NULL," +
                TABLE_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
