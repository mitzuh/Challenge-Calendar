package fi.tuni.challengecalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "cc.db";

    // Table names
    private static final String CHALLENGES = "challenges";
    private static final String COMPLETED = "completed";
    private static final String FAILED = "failed";

    // Table fields
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";

    SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Challenges
        db.execSQL("CREATE TABLE " + CHALLENGES + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(255), " + KEY_DATE + " VARCHAR(255))");

        // Completed Challenges
        db.execSQL("CREATE TABLE " + COMPLETED + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(255), " + KEY_DATE + " VARCHAR(255))");

        // Failed Challenges
        db.execSQL("CREATE TABLE " + FAILED + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(255), " + KEY_DATE + " VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + COMPLETED);
        db.execSQL("DROP TABLE IF EXISTS " + FAILED);
        onCreate(db);
    }

    // CHALLENGES

    public void addChallenge(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, c.getId());
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, c.getDate().toString());

        db.insert(CHALLENGES, null, values);
        db.close();
    }

    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + CHALLENGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Challenge challenge = new Challenge(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
                challenges.add(challenge);
            }
            while (cursor.moveToNext());
        }
        return challenges;
    }

    public void deleteChallenge(int index, int size) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CHALLENGES + " WHERE id = " + index);

        int newID = index-1;
        for (int i=index; i<=size; i++) {
            db.execSQL("UPDATE " + CHALLENGES + " SET id = " + newID + " WHERE id = " + i);
            newID++;
        }
    }

    // COMPLETED CHALLENGES

    public void addCompleted(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String newDate = sdf.format(new Date());

        ContentValues values = new ContentValues();
        values.put(KEY_ID, getCompleted().size()+1);
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, newDate.toString());

        db.insert(COMPLETED, null, values);
        db.close();
    }

    public List<Challenge> getCompleted() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + COMPLETED;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Challenge challenge = new Challenge(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
                challenges.add(challenge);
            }
            while (cursor.moveToNext());
        }
        return challenges;
    }

    // FAILED CHALLENGES

    public void addFailed(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, getFailed().size()+1);
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, c.getDate().toString());

        db.insert(FAILED, null, values);
        db.close();
    }

    public List<Challenge> getFailed() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FAILED;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Challenge challenge = new Challenge(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
                challenges.add(challenge);
            }
            while (cursor.moveToNext());
        }
        return challenges;
    }
}
