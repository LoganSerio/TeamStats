package com.example.logan.test2;


import java.io.Serializable;

class Position implements Serializable{
    public static final String TAG = "Position";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mPositionName;
    private Team mTeam;

    public Position() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getPositionName() {
        return mPositionName;
    }

    public void setPositionName(String mPosition) {
        this.mPositionName = mPosition;
    }

    public Team getTeam() {
        return mTeam;
    }

    public void setTeam(Team mTeam) {
        this.mTeam = mTeam;
    }
    public String toString() {
        return this.mPositionName.toString();
    }
    public String getTeamName() {
        return mTeam.getName();
    }
}
