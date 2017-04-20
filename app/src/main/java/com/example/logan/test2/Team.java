package com.example.logan.test2;

import java.io.Serializable;

/**
 * A class to create team objects.
 */
class Team implements Serializable{
    public static final String TAG = "Position";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mName;

    public Team() {}

    public Team(String name) {
        this.mName = name;
    }
    public long getId() {
        return mId;
    }
    public void setId(long mId) {
        this.mId = mId;
    }
    public String getName() {
        return mName;
    }
    public void setName(String mFirstName) {
        this.mName = mFirstName;
    }
}
