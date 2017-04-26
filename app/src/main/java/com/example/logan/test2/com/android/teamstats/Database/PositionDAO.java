package com.example.logan.test2.com.android.teamstats.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.Base.Team;

import java.util.ArrayList;
import java.util.List;


public class PositionDAO {
    public static final String TAG = "PositionDAO";
    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_POSITION_ID,
            DatabaseHelper.COLUMN_POSITION_NAME };

    public PositionDAO(Context context) {
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

    public Position createPosition(String positionName, long teamId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_POSITION_NAME, positionName);
        values.put(DatabaseHelper.COLUMN_POSITION_TEAM_ID, teamId);
        long insertId = database
                .insert(DatabaseHelper.TABLE_POSITIONS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSITIONS, allColumns,
                DatabaseHelper.COLUMN_POSITION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Position newPosition = cursorToPosition(cursor);
        cursor.close();
        return newPosition;
    }

    public void deletePosition(Position position) {
        long id = position.getId();
        System.out.println("the deleted position has the id: " + id);
        database.delete(DatabaseHelper.TABLE_POSITIONS, DatabaseHelper.COLUMN_POSITION_ID
                + " = " + id, null);
    }

    public List<Position> getPositionsOfTeam(long teamId) {
        List<Position> listPositions = new ArrayList<Position>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_POSITIONS, allColumns,
                DatabaseHelper.COLUMN_POSITION_TEAM_ID + " = ?",
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

        //IF ERROR OCCURS, CHECK !NULL (SEE: TEAMDAO)
        position.setId(cursor.getLong(0));
        position.setPositionName(cursor.getString(1));
        long teamId = cursor.getLong(0);
        TeamDAO dao = new TeamDAO(context);
        Team team = dao.getTeamById(teamId);
        if (team != null)
            position.setTeam(team);

        return position;
    }

    public Position getPositionById(long id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSITIONS, allColumns,
                DatabaseHelper.COLUMN_POSITION_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = cursorToPositionByID(cursor);
        return position;
    }

    protected Position cursorToPositionByID(Cursor cursor) {
        Position position = new Position();
        if(cursor != null && cursor.moveToFirst()){
            position.setId(cursor.getLong(0));
            position.setName(cursor.getString(1));
        }
        return position;
    }
}