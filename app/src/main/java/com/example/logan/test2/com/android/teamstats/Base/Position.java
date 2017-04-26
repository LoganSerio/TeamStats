package com.example.logan.test2.com.android.teamstats.Base;


import java.io.Serializable;

public class Position implements Serializable{
    public static final String TAG = "Position";
    private static final long serialVersionUID = -7406082437623008161L;

    private long id;
    private String positionName;
    private Team team;

    public Position() {
    }

    public long getId() {
        return id;
    }

    public void setId(long posId) {
        this.id = posId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String position) {
        this.positionName = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    public String toString() {
        return this.positionName.toString();
    }
    public String getName() {
        return positionName;
    }
    public void setName(String positionName) {
        this.positionName = positionName;
    }
}