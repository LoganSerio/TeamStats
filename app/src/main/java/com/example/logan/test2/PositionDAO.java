package com.example.logan.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


class PositionDAO {
    public static final String TAG = "PositionDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_POSITION_ID,
            DBHelper.COLUMN_POSITION_NAME };

    public PositionDAO(Context context) {
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

    public Position createPosition(String positionName, long teamId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_POSITION_NAME, positionName);
        values.put(DBHelper.COLUMN_POSITION_TEAM_ID, teamId);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_POSITIONS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_POSITIONS, mAllColumns,
                DBHelper.COLUMN_POSITION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Position newPosition = cursorToPosition(cursor);
        cursor.close();
        return newPosition;
    }

    public Position createPosition(String positionName) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_POSITION_NAME, positionName);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_POSITIONS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_POSITIONS, mAllColumns,
                DBHelper.COLUMN_POSITION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Position newPosition = cursorToPosition(cursor);
        cursor.close();
        return newPosition;

    }

    public void deletePosition(Position position) {
        long id = position.getId();
        System.out.println("the deleted position has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_POSITIONS, DBHelper.COLUMN_POSITION_ID
                + " = " + id, null);
    }

    public List<Position> getAllPositions() {
        List<Position> listPositions = new ArrayList<Position>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_POSITIONS, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Position position = cursorToPosition(cursor);
            listPositions.add(position);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPositions;
    }

    public List<Position> getPositionsOfTeam(long teamId) {
        List<Position> listPositions = new ArrayList<Position>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_POSITIONS, mAllColumns,
                DBHelper.COLUMN_POSITION_TEAM_ID + " = ?",
                new String[] { String.valueOf(teamId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Position position = cursorToPosition(cursor);
            listPositions.add(position);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPositions;
    }

    private Position cursorToPosition(Cursor cursor) {
        Position position = new Position();

        //potential problem later!!!
        position.setId(cursor.getLong(0));
        position.setPositionName(cursor.getString(1));
        // get The company by id

        long teamId = cursor.getLong(0);
        TeamDAO dao = new TeamDAO(mContext);
        Team team = dao.getTeamById(teamId);
        if (team != null)
            position.setTeam(team);

        return position;
    }
}
