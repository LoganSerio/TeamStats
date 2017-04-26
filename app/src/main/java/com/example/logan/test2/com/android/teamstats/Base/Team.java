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

    public Team() {}

    public Team(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long teamId) {
        this.id = teamId;
    }
    public String getName() {
        return name;
    }
    public void setName(String firstName) {
        this.name = firstName;
    }
}
