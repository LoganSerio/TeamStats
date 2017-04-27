package com.example.logan.test2.com.android.teamstats.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.Base.Statistic;

import java.util.ArrayList;
import java.util.List;


public class StatisticDAO {
    public static final String TAG = "StatisticDAO";

    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_STATISTIC_ID,
            DatabaseHelper.COLUMN_STATISTIC_NAME,
            DatabaseHelper.COLUMN_STATISTIC_VALUE,
            DatabaseHelper.COLUMN_STATISTIC_POSITION_ID };

    /**
     * Constructor for the StatisticDAO class
     * @param context gives newly created objects context of the program
     */
    public StatisticDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
        //opens the database
        try {
            open();
        } catch (SQLException e) {
            // creates log for debugging purposes
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Opens the database
     * @throws SQLException if there is an error
     */
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    /**
     * Closes the database
     */
    public void close() {
        databaseHelper.close();
    }

    /**
     * Adds a statistic to the database and returns it
     * @param statisticName the name of the statistic
     * @param statisticValue the value of the statistic
     * @param positionId the id of the position to which the statistic should be linked
     * @return newStatistic the created statistic
     */
    public Statistic createStatistic(String statisticName, String statisticValue, long positionId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_STATISTIC_NAME, statisticName);
        values.put(DatabaseHelper.COLUMN_STATISTIC_VALUE, statisticValue);
        values.put(DatabaseHelper.COLUMN_STATISTIC_POSITION_ID, positionId);
        long insertId = database.insert(DatabaseHelper.TABLE_STATISTICS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_STATISTICS, allColumns,
                DatabaseHelper.COLUMN_STATISTIC_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Statistic newStatistic = cursorToStatistic(cursor);
        cursor.close();
        return newStatistic;
    }

    /**
     * Updates the statistics column of the statistic table
     * @param stat
     */
    public void updateStatistics(Statistic stat) {
        ContentValues values = new ContentValues();
        values.put("statistic_value", stat.getStatisticValue());
        //updates the database
        database.update("statistics", values, DatabaseHelper.COLUMN_STATISTIC_ID+"="+stat.getId(), null);
    }

    /**
     * Deletes a statistic from the statistics table
     * @param statistic the statistic to be deleted
     */
    public void deleteStatistic(Statistic statistic) {
        long id = statistic.getId(); //id of the statistic
        //deletes the statistic from the database
        database.delete(DatabaseHelper.TABLE_STATISTICS, DatabaseHelper.COLUMN_STATISTIC_ID
                + " = " + id, null);
    }

    /**
     * Gets all of the statistics linked to a position given the position's id
     * @param positionId the id of the position
     * @return the list of the statistics
     */
    public List<Statistic> getStatisticsOfPosition(long positionId) {
        List<Statistic> listStatistics = new ArrayList<Statistic>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_STATISTICS, allColumns,
                DatabaseHelper.COLUMN_STATISTIC_POSITION_ID + " = ?",
                new String[] { String.valueOf(positionId) }, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Statistic statistic = cursorToStatistic(cursor);
            listStatistics.add(statistic);
            cursor.moveToNext();
        }
        cursor.close();
        return listStatistics;
    }

    /**
     * Iterates the cursor to a statistic
     * @param cursor the cursor
     * @return the statistic
     */
    private Statistic cursorToStatistic(Cursor cursor) {
        Statistic statistic = new Statistic();
        statistic.setId(cursor.getLong(0));
        statistic.setStatisticName(cursor.getString(1));
        statistic.setStatisticValue(cursor.getString(2));
        // get The position by id
        long positionId = cursor.getLong(3);
        PositionDAO dao = new PositionDAO(context);
        Position position = dao.getPositionById(positionId);
        if (position != null)
            statistic.setPosition(position);
        return statistic;
    }
}
