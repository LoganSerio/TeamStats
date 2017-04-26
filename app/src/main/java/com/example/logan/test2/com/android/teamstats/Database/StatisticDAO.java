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

    public StatisticDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public Statistic createStatistic(String statisticName, String statisticValue, long positionId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_STATISTIC_NAME, statisticName);
        values.put(DatabaseHelper.COLUMN_STATISTIC_VALUE, statisticValue);
        values.put(DatabaseHelper.COLUMN_STATISTIC_POSITION_ID, positionId);
        long insertId = database
                .insert(DatabaseHelper.TABLE_STATISTICS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_STATISTICS, allColumns,
                DatabaseHelper.COLUMN_STATISTIC_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Statistic newStatistic = cursorToStatistic(cursor);
        cursor.close();
        return newStatistic;
    }

    public void updateStatistics(Statistic stat) {
        ContentValues values = new ContentValues();
        values.put("statistic_value", stat.getStatisticValue());
        database.update("statistics", values, DatabaseHelper.COLUMN_STATISTIC_ID+"="+stat.getId(), null);
    }

    public void deleteStatistic(Statistic statistic) {
        long id = statistic.getId();
        System.out.println("the deleted statistic has the id: " + id);
        database.delete(DatabaseHelper.TABLE_STATISTICS, DatabaseHelper.COLUMN_STATISTIC_ID
                + " = " + id, null);
    }


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
        // make sure to close the cursor
        cursor.close();
        return listStatistics;
    }

    private Statistic cursorToStatistic(Cursor cursor) {
        Statistic statistic = new Statistic();

        //IF ERROR OCCURS, CHECK !NULL (SEE: TEAMDAO)
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
