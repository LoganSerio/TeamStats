package com.example.logan.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * A class that allows for the position stats page to be displayed.
 */
public class PositionStats extends AppCompatActivity {

    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_stats);
    }
}
