package com.example.logan.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * A class that makes the homepage of the app along
 */
public class Homepage extends AppCompatActivity {
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
        btnCreateNewTeam = (Button) findViewById(R.id.createTeamButton);
        btnExistingTeam = (Button) findViewById(R.id.existingTeamButton);
        btnSettings = (Button) findViewById(R.id.settingsButton);

        btnCreateNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,CreateTeamPopUp.class));
            }
        });

        btnExistingTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,ExistingTeam.class));
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,Settings.class));
            }
        });
    }
}
