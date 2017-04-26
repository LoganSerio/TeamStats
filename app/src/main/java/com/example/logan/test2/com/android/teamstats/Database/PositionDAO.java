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

    /**
     * Constructor for the PositionDAO class
     * @param context gives newly created objects context of the program
     */
    public PositionDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            //creates a log message for debugging purposes
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
     * Method to create a new position and add it to the database
     * @param positionName the name of the position to be created
     * @param teamId id of the team the position is linked with
     * @return the newly created position
     */
    public Position createPosition(String positionName, long teamId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_POSITION_NAME, positionName);
        values.put(DatabaseHelper.COLUMN_POSITION_TEAM_ID, teamId);
        long insertId = database.insert(DatabaseHelper.TABLE_POSITIONS, null, values);
        // creates cursor to provide random read-write access to the result set returned by a database query.
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSITIONS, allColumns,
                DatabaseHelper.COLUMN_POSITION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst(); //moves the cursor to the first location
        Position newPosition = cursorToPosition(cursor); //converts the cursor to a position
        cursor.close(); //closes the cursor
        return newPosition; //returns the the position
    }

    /**
     * Method to delete a position
     * @param position the position to be deleted
     */
    public void deletePosition(Position position) {
        long id = position.getId(); // the id of the position to be deleted
        //calls the delete function of the SQLiteDatabase class
        database.delete(DatabaseHelper.TABLE_POSITIONS, DatabaseHelper.COLUMN_POSITION_ID
                + " = " + id, null);
    }

    /**
     * Gets all of the positions of a team given its id
     * @param teamId the id of the team
     * @return return a list of positions
     */
    public List<Position> getPositionsOfTeam(long teamId) {
        List<Position> listPositions = new ArrayList<Position>();

        //initializes the cursor to provide read-write access to the database query
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSITIONS, allColumns,
                DatabaseHelper.COLUMN_POSITION_TEAM_ID + " = ?",
                new String[] { String.valueOf(teamId) }, null, null, null);

        cursor.moveToFirst(); //moves the cursor to the first position
        //iterates through the database to get all of the positions and adds them to the list
        while (!cursor.isAfterLast()) {
            Position position = cursorToPosition(cursor);
            listPositions.add(position);
            cursor.moveToNext();
        }
        cursor.close(); //closes the cursor
        return listPositions; //returns the list of positions for the given team
    }

    /**
     * Converts the cursor to the type Position
     * @param cursor the cursor providing read-write access
     * @return the position
     */
    private Position cursorToPosition(Cursor cursor) {
        Position position = new Position(); //initializes position
        position.setId(cursor.getLong(0)); //sets the id of the position to the first column in the table
        position.setPositionName(cursor.getString(1)); //sets the position name to the second column
        long teamId = cursor.getLong(0); //sets the teamId
        TeamDAO dao = new TeamDAO(context); //initializes a TeamDAO object
        Team team = dao.getTeamById(teamId); //gets the id of the team
        if (team != null)
            position.setTeam(team); //sets the teamId column of the position to the team's id
        return position; //returns the position
    }

    /**
     * Gets a position given its id
     * @param id the id of the position to be returned
     * @return the position
     */
    public Position getPositionById(long id) {
        //initializes the cursor to the database query
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSITIONS, allColumns,
                DatabaseHelper.COLUMN_POSITION_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Position position = cursorToPositionByID(cursor);
        return position; //returns the position
    }

    /**
     * Converts a cursror to position for the getPositionById method
     * @param cursor
     * @return
     */
    protected Position cursorToPositionByID(Cursor cursor) {
        Position position = new Position();
        //checks to see if the position is null and is in the first position
        if(cursor != null && cursor.moveToFirst()){
            position.setId(cursor.getLong(0));
            position.setName(cursor.getString(1));
        }
        return position; //returns the position
    }
}