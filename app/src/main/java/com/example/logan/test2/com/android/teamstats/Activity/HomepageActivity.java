package com.example.logan.test2.com.android.teamstats.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.logan.test2.com.android.teamstats.R;

/**
 * A class that makes the homepage of the app
 */
public class HomepageActivity extends AppCompatActivity {
    Button btnCreateNewTeam;
    Button btnExistingTeam;
    Button btnSettings;

    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        btnCreateNewTeam = (Button) findViewById(R.id.btn_create_team);
        btnExistingTeam = (Button) findViewById(R.id.btn_existing_teams);
        btnSettings = (Button) findViewById(R.id.btn_settings);

        btnCreateNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this,CreateTeamActivity.class));
            }
        });

        btnExistingTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, ListTeamsActivity.class));
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this,SettingsActivity.class));
            }
        });


    }
}
