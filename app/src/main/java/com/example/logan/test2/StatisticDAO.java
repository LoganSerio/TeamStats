package com.example.logan.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


class StatisticDAO {
    public static final String TAG = "StatisticDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_STATISTIC_ID,
            DBHelper.COLUMN_STATISTIC_NAME,
            DBHelper.COLUMN_STATISTIC_VALUE,
            DBHelper.COLUMN_STATISTIC_POSITION_ID };

    public StatisticDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Statistic createStatistic(String statisticName, String statisticValue, long positionId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_STATISTIC_NAME, statisticName);
        values.put(DBHelper.COLUMN_STATISTIC_VALUE, statisticValue);
        values.put(DBHelper.COLUMN_STATISTIC_POSITION_ID, positionId);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_STATISTICS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_STATISTICS, mAllColumns,
                DBHelper.COLUMN_STATISTIC_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Statistic newStatistic = cursorToStatistic(cursor);
        cursor.close();
        return newStatistic;
    }

    public void updateStatistics(ArrayList<Statistic> list) {
        for(int i = 0; i < list.size() - 1; i++) {
            Statistic stat = (Statistic) list.get(i);
            long id = stat.getId();
            String value = stat.getStatisticValue();
            String sql = "UPDATE STATISTICS SET statistic_value = " + value + " WHERE _id = " + id + ";";
            mDatabase.execSQL(sql);
        }
    }

    public void deleteStatistic(Statistic statistic) {
        long id = statistic.getId();
        System.out.println("the deleted statistic has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_STATISTICS, DBHelper.COLUMN_STATISTIC_ID
                + " = " + id, null);
    }

    public List<Statistic> getAllStatistics() {
        List<Statistic> listStatistics = new ArrayList<Statistic>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_STATISTICS, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Statistic statistic = cursorToStatistic(cursor);
            listStatistics.add(statistic);
            cursor.moveToNext();
        }
        // close the cursor
        cursor.close();
        return listStatistics;
    }

    public List<Statistic> getStatisticsOfPosition(long positionId) {
        List<Statistic> listStatistics = new ArrayList<Statistic>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_STATISTICS, mAllColumns,
                DBHelper.COLUMN_STATISTIC_POSITION_ID + " = ?",
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
        PositionDAO dao = new PositionDAO(mContext);
        Position position = dao.getPositionById(positionId);
        if (position != null)
            statistic.setPosition(position);

        return statistic;
    }
}
