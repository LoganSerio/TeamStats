package com.example.logan.test2.com.android.teamstats.Base;

import com.example.logan.test2.com.android.teamstats.Base.Position;

import java.io.Serializable;

/**
 * A class to create statistic objects
 */
public class Statistic implements Serializable {
    public static final String TAG = "Statistic";
    private static final long serialVersionUID = -7406082437623008161L;

    private long id;
    private String statisticName;
    private String statisticValue;
    private Position position;

    /**
     * Constructs a Statistic object
     */
    public Statistic() {

    }

    /**
     * Gets the id of a Statistic object
     * @return The id of the Statistic object
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of a Statistic object
     * @param statId The id the user wants for the Statistics object
     */
    public void setId(long statId) {
        this.id = statId;
    }

    /**
     * Gets the name of a Statistics object
     * @return The name of a Statistics object
     */
    public String getStatisticName() {
        return statisticName;
    }

    /**
     * Sets the name of a Statistics object
     * @param statistic The name the user wants for the Statistic object
     */
    public void setStatisticName(String statistic) {
        this.statisticName = statistic;
    }

    /**
     * Gets the value of a Statistics object
     * @return The value of a Statistics object
     */
    public String getStatisticValue() {
        return statisticValue;
    }

    /**
     * Sets the value of a Statistics object
     * @param value The value the user wants for the Statistics object
     */
    public void setStatisticValue(String value) {
        this.statisticValue = value;
    }

    /**
     * Gets the Position that the Statistics object is tied to
     * @return The Position that the Statistics object is tied to
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the Position that the Statistic object is tied to
     * @param mPosition The Position that the user wants to set for the Statisitc
     */
    public void setPosition(Position mPosition) {
        this.position = mPosition;
    }
}