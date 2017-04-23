package com.example.logan.test2;

import java.io.Serializable;

public class Statistic implements Serializable {
    public static final String TAG = "Statistic";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mStatisticName;
    private String mStatisticValue;
    private Position mPosition;

    public Statistic() {

    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getStatisticName() {
        return mStatisticName;
    }

    public void setStatisticName(String mStatistic) {
        this.mStatisticName = mStatistic;
    }

    public String getStatisticValue() {
        return mStatisticValue;
    }

    public void setStatisticValue(String value) {
        this.mStatisticValue = value;
    }

    public Position getPosition() {
        return mPosition;
    }

    public void setPosition(Position mPosition) {
        this.mPosition = mPosition;
    }

    public String toString() {
        return this.mStatisticName.toString();
    }

    public String getPositionName() {
        return mPosition.getName();
    }

}
