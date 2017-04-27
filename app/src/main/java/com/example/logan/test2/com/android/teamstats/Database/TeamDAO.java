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

/**
 * A class that connects to the team data access object.
 */
public class TeamDAO {
    public static final String TAG = "TeamDAO";
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;
    private String[] allColumns = { DatabaseHelper.COLUMN_TEAM_ID,
            DatabaseHelper.COLUMN_TEAM_NAME };

    /**
     * Creates a team data access object.
     * @param context The request to the database.
     */
    public TeamDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context); //right here
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
        database = databaseHelper.getWritableDatabase();
    }

    /**
     * Closes the database helper.
     */
    public void close() {
        databaseHelper.close();
    }

    /**
     * Gathers team info from the database.
     * @param name the name of the team.
     * @return A completed team full of the desired information.
     */
    public Team createTeam(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEAM_NAME, name);
        long insertId = database
                .insert(DatabaseHelper.TABLE_TEAMS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_TEAMS, allColumns,
                DatabaseHelper.COLUMN_TEAM_ID + " = " + insertId, null, null,
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
        PositionDAO positionDao = new PositionDAO(context);
        List<Position> listPositions = positionDao.getPositionsOfTeam(id);
        if (listPositions != null && !listPositions.isEmpty()) {
            for (Position e : listPositions) {
                positionDao.deletePosition(e);
            }
        }
        database.delete(DatabaseHelper.TABLE_TEAMS, DatabaseHelper.COLUMN_TEAM_ID
                + " = " + id, null);
    }

    /**
     * A method that gets all the teams from the database.
     * @return A list containing all the team names.
     */
    public List<Team> getAllTeams() {
        List<Team> listTeams = new ArrayList<Team>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_TEAMS, allColumns,
                null, null, null, null, null);

        if (cursor != null) { //drop down a line
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
        Cursor cursor = database.query(DatabaseHelper.TABLE_TEAMS, allColumns,
                DatabaseHelper.COLUMN_TEAM_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Team team = cursorToTeamByID(cursor);
        return team;
    }

    /**
     * Returns a collection of database info with regard to ID and name.
     * @param cursor the cursor being used for the database.
     * @return the team object with ID and name.
     */
    protected Team cursorToTeam(Cursor cursor) {
        Team team = new Team();
        if(cursor != null /*&& cursor.moveToFirst()*/){
            team.setId(cursor.getLong(0));
            team.setName(cursor.getString(1));
        }
        return team;
    }

    /**
     * Returns a collection of database info with regard to id and name, specifically for the
     * getTeamById method
     * @param cursor the cursor being used for the database
     * @return the team object
     */
    protected Team cursorToTeamByID(Cursor cursor) {
        Team team = new Team();
        if(cursor != null && cursor.moveToFirst()){
            team.setId(cursor.getLong(0));
            team.setName(cursor.getString(1));
        }
        return team;
    }
}
