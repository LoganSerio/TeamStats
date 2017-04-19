package com.example.logan.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trevor on 4/18/17.
 */

class TeamDAO {
    public static final String TAG = "TeamDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_TEAM_ID,
            DBHelper.COLUMN_TEAM_NAME };

    public TeamDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context); //right here
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

    public Team createTeam(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TEAM_NAME, name);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_TEAMS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TEAMS, mAllColumns,
                DBHelper.COLUMN_TEAM_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Team newTeam = cursorToTeam(cursor);
        cursor.close();
        return newTeam;
    }

    public void deleteTeam(Team team) {
        long id = team.getId();
        // delete all positions of this company
        PositionDAO positionDao = new PositionDAO(mContext);
        List<Position> listPositions = positionDao.getPositionsOfTeam(id);
        if (listPositions != null && !listPositions.isEmpty()) {
            for (Position e : listPositions) {
                positionDao.deletePosition(e);
            }
        }

        System.out.println("the deleted team has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_TEAMS, DBHelper.COLUMN_TEAM_ID
                + " = " + id, null);
    }

    public List<Team> getAllTeams() {
        List<Team> listTeams = new ArrayList<Team>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TEAMS, mAllColumns,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Team team = cursorToTeam(cursor);
                listTeams.add(team);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listTeams;
    }

    public Team getTeamById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TEAMS, mAllColumns,
                DBHelper.COLUMN_TEAM_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Team team = cursorToTeam(cursor);
        return team;
    }

    protected Team cursorToTeam(Cursor cursor) {
        Team team = new Team();
        team.setId(cursor.getLong(0));
        team.setName(cursor.getString(1));
        return team;
    }

}
