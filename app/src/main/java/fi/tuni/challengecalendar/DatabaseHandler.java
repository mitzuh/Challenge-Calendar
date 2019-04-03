package fi.tuni.challengecalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "cc.db";

    // Table name
    private static final String TABLE_NAME = "challenges";

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
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(255), " + KEY_DATE + " VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addChallenge(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, c.getId());
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, c.getDate().toString());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

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
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id = " + index);

        int newID = index-1;
        for (int i=index; i<=size; i++) {
            db.execSQL("UPDATE " + TABLE_NAME + " SET id = " + newID + " WHERE id = " + i);
            newID++;
        }
    }
}
