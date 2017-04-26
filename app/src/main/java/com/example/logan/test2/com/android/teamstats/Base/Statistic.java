package com.example.logan.test2.com.android.teamstats.Base;

import com.example.logan.test2.com.android.teamstats.Base.Position;

import java.io.Serializable;

public class Statistic implements Serializable {
    public static final String TAG = "Statistic";
    private static final long serialVersionUID = -7406082437623008161L;

    private long id;
    private String statisticName;
    private String statisticValue;
    private Position position;

    public Statistic() {

    }

    public long getId() {
        return id;
    }

    public void setId(long statId) {
        this.id = statId;
    }

    public String getStatisticName() {
        return statisticName;
    }

    public void setStatisticName(String statistic) {
        this.statisticName = statistic;
    }

    public String getStatisticValue() {
        return statisticValue;
    }

    public void setStatisticValue(String value) {
        this.statisticValue = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position mPosition) {
        this.position = mPosition;
    }

    public String toString() {
        return this.statisticName.toString();
    }

}