package com.example.logan.test2.com.android.teamstats.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Creates a helper class for connecting to the database.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";

    // teams table
    public static final String TABLE_TEAMS = "teams";
    public static final String COLUMN_TEAM_ID = "_id";
    public static final String COLUMN_TEAM_NAME = "team_name";

    // positions table
    public static final String TABLE_POSITIONS = "positions";
    public static final String COLUMN_POSITION_ID = COLUMN_TEAM_ID;
    public static final String COLUMN_POSITION_NAME = "position_name";
    public static final String COLUMN_POSITION_TEAM_ID = "team_id";

    // statistics table
    public static final String TABLE_STATISTICS = "statistics";
    public static final String COLUMN_STATISTIC_ID = COLUMN_POSITION_ID;
    public static final String COLUMN_STATISTIC_NAME = "statistic_name";
    public static final String COLUMN_STATISTIC_VALUE = "statistic_value";
    public static final String COLUMN_STATISTIC_POSITION_ID = "position_id";


    private static final String DATABASE_NAME = "teams.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement of the statistic table creation
    private static final String SQL_CREATE_TABLE_STATISTICS = "CREATE TABLE " + TABLE_STATISTICS + "("
            + COLUMN_STATISTIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STATISTIC_NAME + " TEXT NOT NULL, "
            + COLUMN_STATISTIC_VALUE + " TEXT NOT NULL, "
            + COLUMN_STATISTIC_POSITION_ID + " INTEGER NOT NULL "
            +");";

    // SQL statement of the position table creation
    private static final String SQL_CREATE_TABLE_POSITIONS = "CREATE TABLE " + TABLE_POSITIONS + "("
            + COLUMN_POSITION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_POSITION_NAME + " TEXT NOT NULL, "
            + COLUMN_POSITION_TEAM_ID + " INTEGER NOT NULL "
            +");";

    // SQL statement of the teams table creation
    private static final String SQL_CREATE_TABLE_TEAMS = "CREATE TABLE " + TABLE_TEAMS + "("
            + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEAM_NAME + " TEXT NOT NULL "
            +");";


    /**
     * A constructor for the DatabaseHelper class
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param database the database to be created
     */
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_TEAMS);
        database.execSQL(SQL_CREATE_TABLE_POSITIONS);
        database.execSQL(SQL_CREATE_TABLE_STATISTICS);
    }

    @Override
    /**
     * Updates the database to a newer version.
     * @param db The database
     * @param oldVersion The old version of the database.
     * @param newVersion The new version of the database.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Adds log message for debugging purposes
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to "+ newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);

        // recreate the tables
        onCreate(db);
    }
}
