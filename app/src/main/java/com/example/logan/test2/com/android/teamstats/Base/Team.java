package com.example.logan.test2.com.android.teamstats.Base;

import java.io.Serializable;

/**
 * A class to create team objects.
 */
public class Team implements Serializable{
    public static final String TAG = "Position";
    private static final long serialVersionUID = -7406082437623008161L;

    private long id;
    private String name;

    /**
     * Constructs a new Team object
     */
    public Team() {}

    /**
     * Parameterized constructor that makes a new Team object and assigns a name to it
     * @param name the name of the Team object
     */
    public Team(String name) {
        this.name = name;
    }

    /**
     * Gets the id of the Team object
     * @return The id of the Team object
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id for the Team object
     * @param teamId The id the user wants for their Team object
     */
    public void setId(long teamId) {
        this.id = teamId;
    }

    /**
     * Gets the name of the Team object
     * @return The name of the Team object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the Team object
     * @param name The name the user wants for their Team object
     */
    public void setName(String name) {
        this.name = name;
    }
}
