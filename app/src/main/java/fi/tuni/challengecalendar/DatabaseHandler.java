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

/**
 * Class for handling the data in the local database.
 *
 * <p>
 *     SQLite database manager class, which makes all the needed database tables.
 *     Every action involving the modifying of database tables are done here.
 * </p>
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "cc.db";

    // Table names
    private static final String CHALLENGES = "challenges";
    private static final String COMPLETED = "completed";
    private static final String FAILED = "failed";

    private static final String POINTS = "points";

    // Table fields
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_POINTS = "points";

    private static final String KEY_AMOUNT = "amount";

    SQLiteDatabase database;

    /**
     * Constructor for the DatabaseHandler.
     *
     * @param context Application context.
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    /**
     * Initialization of the database.
     *
     * <p>
     *     Makes all the database tables and their columns with SQL commands.
     * </p>
     *
     * @param db Database to be created locally.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Challenges
        db.execSQL("CREATE TABLE " + CHALLENGES + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(100), " + KEY_DATE + " VARCHAR(10), " + KEY_POINTS +
                " VARCHAR(5))");

        // Completed Challenges
        db.execSQL("CREATE TABLE " + COMPLETED + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(100), " + KEY_DATE + " VARCHAR(10), " + KEY_POINTS +
                " VARCHAR(5))");

        // Failed Challenges
        db.execSQL("CREATE TABLE " + FAILED + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " VARCHAR(100), " + KEY_DATE + " VARCHAR(10), " + KEY_POINTS +
                " VARCHAR(5))");

        // Points
        db.execSQL("CREATE TABLE " + POINTS + " ( " + KEY_AMOUNT + " INTEGER)");
    }

    /**
     * Removes existing tables and creates new ones, with the same name
     *
     * <p>
     *     This method is used to clear all the data from the database.
     *     Table rows are deleted and new tables are made with empty data.
     * </p>
     *
     * @param db Database, which holds all the data of the tables.
     * @param oldVersion Version number for previous version.
     * @param newVersion Version number for the new version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + COMPLETED);
        db.execSQL("DROP TABLE IF EXISTS " + FAILED);
        db.execSQL("DROP TABLE IF EXISTS " + POINTS);
        onCreate(db);
    }

    // CHALLENGES

    /**
     * Adds a new Challenge to CHALLENGES table.
     *
     * @param c Challenge to be added to the table.
     */
    public void addChallenge(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, c.getId());
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, c.getDate().toString());
        values.put(KEY_POINTS, c.getPoints());

        db.insert(CHALLENGES, null, values);
        db.close();
    }

    /**
     * Gets all the Challenges from CHALLENGES table in the database and converts them
     * into a List.
     *
     * @return List with all the upcoming Challenges.
     */
    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + CHALLENGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Challenge challenge = new Challenge(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3));
                challenges.add(challenge);
            }
            while (cursor.moveToNext());
        }
        return challenges;
    }

    /**
     * Deletes a Challenge from the CHALLENGES table.
     *
     * <p>
     *     Deletes a single Challenge from the CHALLENGES table and sort the remaining
     *     Challenges id:s accordingly.
     * </p>
     *
     * @param index Id of the Challenge to be deleted.
     * @param size Number of upcoming Challenges.
     */
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

    /**
     * Adds Challenge to COMPLETED table.
     *
     * @param c Challenge to be added to COMPLETED table.
     */
    public void addCompleted(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String newDate = sdf.format(new Date());

        ContentValues values = new ContentValues();
        values.put(KEY_ID, getCompleted().size()+1);
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, newDate.toString());
        values.put(KEY_POINTS, c.getPoints());

        db.insert(COMPLETED, null, values);
        db.close();
    }

    /**
     * Gets all the Challenges from COMPLETED table in the database and converts them
     * into a List.
     *
     * @return List with all the completed Challenges.
     */
    public List<Challenge> getCompleted() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + COMPLETED;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Challenge challenge = new Challenge(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3));
                challenges.add(challenge);
            }
            while (cursor.moveToNext());
        }
        return challenges;
    }

    // FAILED CHALLENGES

    /**
     * Adds Challenge to FAILED table.
     *
     * @param c Challenge to be added to FAILED table.
     */
    public void addFailed(Challenge c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, getFailed().size()+1);
        values.put(KEY_NAME, c.getName());
        values.put(KEY_DATE, c.getDate().toString());
        values.put(KEY_POINTS, c.getPoints());

        db.insert(FAILED, null, values);
        db.close();
    }

    /**
     * Gets all the Challenges from FAILED table in the database and converts them
     * into a List.
     *
     * @return List with all the failed Challenges.
     */
    public List<Challenge> getFailed() {
        List<Challenge> challenges = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FAILED;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Challenge challenge = new Challenge(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3));
                challenges.add(challenge);
            }
            while (cursor.moveToNext());
        }
        return challenges;
    }

    // POINTS

    /**
     * Adds completion points from completed Challenge to the database.
     *
     * @param completionPoints Points from completed Challenge.
     */
    public void addPoints(int completionPoints) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, getTotalPoints() + completionPoints);

        db.insert(POINTS, null, values);
        db.close();
    }

    /**
     * Gets current completion points from the database.
     *
     * @return Current total completion points.
     */
    public int getTotalPoints() {
        String selectQuery = "SELECT * FROM " + POINTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int totalPoints = 0;

        if (cursor.moveToFirst()) {
            do {
                totalPoints = cursor.getInt(0);
            }
            while (cursor.moveToNext());
        }

        return totalPoints;
    }
}
