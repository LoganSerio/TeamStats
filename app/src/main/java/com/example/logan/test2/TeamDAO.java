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

/**
 * A class that connects to the team data access object.
 */
class TeamDAO {
    public static final String TAG = "TeamDAO";
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_TEAM_ID,
            DBHelper.COLUMN_TEAM_NAME };

    /**
     * Creates a team data access object.
     * @param context The request to the database.
     */
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

    /**
     * Connects to the database helper.
     * @throws SQLException
     */
    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    /**
     * Closes the database helper.
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Gathers team info from the database.
     * @param name the name of the team.
     * @return A completed team full of the desired information.
     */
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

    /**
     * Deletes the team from the database.
     * @param team The team object that is desired to be deleted.
     */
    public void deleteTeam(Team team) {
        long id = team.getId();
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

    /**
     * A method that gets al the teams from the database.
     * @return A list containing all the team names.
     */
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
            cursor.close();
        }
        return listTeams;
    }

    /**
     * A method that gets the team from the database according to ID.
     * @param id the ID of the desired team.
     * @return the team associated with the desired ID.
     */
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

    /**
     * Returns a collection of database info with regard to ID and name.
     * @param cursor the cursor being used for the database.
     * @return the team object with ID and name.
     */
    protected Team cursorToTeam(Cursor cursor) {
        Team team = new Team();
        if( cursor != null && cursor.moveToFirst() ) {
            team.setId(cursor.getLong(0));
            team.setName(cursor.getString(1));
            cursor.close();
        }
        return team;
    }
}
