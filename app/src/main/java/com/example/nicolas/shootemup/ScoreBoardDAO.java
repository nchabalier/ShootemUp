package com.example.nicolas.shootemup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Nicolas on 12/11/2016.
 */

public class ScoreBoardDAO {

    public static final String TABLE_NAME = "scoreboard";
    public static final String KEY = "id";
    public static final String NAME = "name";
    public static final String SCORE = "score";
    public static final String SHOTSPEED = "shot speed";
    public static final String SHIPSPEED = "ship speed";
    public static final String WEAPONTYPE = "weapon type";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + SCORE + " INTEGER);";
    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    private final static int VERSION = 1;
    private final static String NOM = "database.db";
    private SQLiteDatabase mDb = null;
    private DataBaseHandler mHandler = null;

    public ScoreBoardDAO(Context pContext) {
        this.mHandler = new DataBaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public void add(ScoreBoard scoreBoard) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY, scoreBoard.getId());
        contentValues.put(NAME, scoreBoard.getName());
        contentValues.put(SCORE, scoreBoard.getScore());
        mDb.insert(TABLE_NAME, null,contentValues);
    }

    public void addScore(ScoreBoard scoreBoard) {

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        mDb.beginTransaction();
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY, scoreBoard.getId());
            contentValues.put(NAME, scoreBoard.getName());
            contentValues.put(SCORE, scoreBoard.getScore());
            contentValues.put(SHOTSPEED, scoreBoard.getShootSpeed());
            contentValues.put(SHIPSPEED, scoreBoard.getShipSpeed());
            contentValues.put(WEAPONTYPE, scoreBoard.getWeaponType().name());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            mDb.insertOrThrow(TABLE_NAME, null, contentValues);
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("ScoreBoardDao", "Error while trying to add scoreboard to database");
        } finally {
            mDb.endTransaction();
        }
    }

    public List<ScoreBoard> getAllScores() {
        List<ScoreBoard> scores = new ArrayList<>();


        String SCORES_SELECT_QUERY =
                String.format("SELECT * FROM %s",TABLE_NAME);


        Cursor cursor = mDb.rawQuery(SCORES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    ScoreBoard scoreBoard = new ScoreBoard();
                    scoreBoard.setId(cursor.getLong(cursor.getColumnIndex(KEY)));
                    scoreBoard.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    scoreBoard.setScore(cursor.getLong(cursor.getColumnIndex(SCORE)));
                    scoreBoard.setShootSpeed(cursor.getInt(cursor.getColumnIndex(SHOTSPEED)));
                    scoreBoard.setShipSpeed(cursor.getInt(cursor.getColumnIndex(SHIPSPEED)));
                    switch (cursor.getString(cursor.getColumnIndex(WEAPONTYPE))){
                        case "BASE":
                            scoreBoard.setWeaponType(TypeWeapon.BASE);
                            break;
                        case "BAZOOKA":
                            scoreBoard.setWeaponType(TypeWeapon.BAZOOKA);
                            break;
                        case "GATTELING":
                            scoreBoard.setWeaponType(TypeWeapon.GATTELING);
                            break;
                    }

                    scores.add(scoreBoard);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ScoreBoardDAO", "Error while trying to get scores from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        Collections.sort(scores,new ScoreComparator());

        return scores;
    }

    public List<String> getAllSNames() {
        List<String> names = new ArrayList<String>();
        List<ScoreBoard> scores = this.getAllScores();

        for(ScoreBoard scoreBoard : scores) {
            names.add(scoreBoard.getName());
        }

        return names;
    }

    public void createDefaultScoreBoard() {
        this.addScore(new ScoreBoard(1, "Chab's", 999999999,999,99,TypeWeapon.BAZOOKA));
        this.addScore(new ScoreBoard(2, "Chuck Norris", 999999998,998,98,TypeWeapon.BAZOOKA));
        this.addScore(new ScoreBoard(3, "Bob", 100000,15,10,TypeWeapon.BASE));
        this.addScore(new ScoreBoard(4, "The boss", 12345,10,10,TypeWeapon.BASE));
    }


    public void delete(long id) {
        // TODO
    }


    public void modify(ScoreBoard scoreBoard) {
        // TODO
    }

    public void deleteAllScores() {
        mDb.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            mDb.delete(TABLE_NAME, null, null);
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("ScoreBoardDAO", "Error while trying to delete all posts and users");
        } finally {
            mDb.endTransaction();
        }
    }

    private class ScoreComparator implements Comparator<ScoreBoard> {
        public int compare(ScoreBoard scoreBoard1, ScoreBoard scoreBoard2) {
            long score1 = scoreBoard1.getScore();
            long score2 = scoreBoard2.getScore();

            if(score1<score2)
                return 1;
            if(score2<score1)
                return -1;
            return 0;
        }

    }
}
