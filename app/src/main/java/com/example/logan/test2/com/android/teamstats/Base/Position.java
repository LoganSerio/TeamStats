package com.example.logan.test2.com.android.teamstats.Base;


import java.io.Serializable;

/**
 * A class that creates Position objects
 */
public class Position implements Serializable{
    public static final String TAG = "Position";
    private static final long serialVersionUID = -7406082437623008161L;

    private long id;
    private String positionName;
    private Team team;

    /**
     * Creates a Position object
     */
    public Position() {
    }

    /**
     * Gets the id of the Position object
     * @return The id of the Position object
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id for the Position object
     * @param posId The id the user wants for the Position object
     */
    public void setId(long posId) {
        this.id = posId;
    }

    /**
     * Gets the name of the Position object
     * @return The name of the Position object
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Sets the name of the Position object
     * @param position The name the user wants to set for the Position object
     */
    public void setPositionName(String position) {
        this.positionName = position;
    }

    /**
     * Gets the Team associated with the Position object
     * @return The Team associated with the Position object
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets the Team associated with the Position object
     * @param team The Team the user wants to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Converts the position name into a string
     * @return A string containing the name of the Position object
     */
    public String toString() {
        return this.positionName.toString();
    }

    /**
     * Gets the name of the position object
     * @return The name of the Position object
     */
    public String getName() {
        return positionName;
    }

    /**
     * Sets the name for the Position object
     * @param positionName The name the user wants for a Position object
     */
    public void setName(String positionName) {
        this.positionName = positionName;
    }
}