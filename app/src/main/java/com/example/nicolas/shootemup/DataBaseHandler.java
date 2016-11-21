package com.example.nicolas.shootemup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nicolas on 12/11/2016.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final String SCOARBOARD_KEY = "id";
    public static final String SCOARBOARD_NAME = "name";
    public static final String SCOARBOARD_SCORE = "score";
    public static final String SCOARBOARD_TABLE_NAME = "ScoreBoard";

    public static final String SCOARBOARD_TABLE_CREATE =
            "CREATE TABLE " + SCOARBOARD_TABLE_NAME + " (" +
                    SCOARBOARD_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SCOARBOARD_NAME + " TEXT, " +
                    SCOARBOARD_SCORE + " INTEGER);";
    public static final String SCOARBOARD_TABLE_DROP = "DROP TABLE IF EXISTS " + SCOARBOARD_TABLE_NAME + ";";

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCOARBOARD_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SCOARBOARD_TABLE_DROP);
        onCreate(db);
    }

}
