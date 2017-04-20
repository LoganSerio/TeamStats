package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * A class that adds support for the user to add positions.
 */
public class TeamPositions extends AppCompatActivity {
    ImageButton btnAdd;
    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_positions);

        //final Team TEAM = (Team) getIntent().getSerializableExtra("Team");


        btnAdd = (ImageButton) findViewById(R.id.btn_add_position);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Team team = (Team) getIntent().getSerializableExtra("Team");
                Intent intent = new Intent(TeamPositions.this,AddPositionActivity.class);
                intent.putExtra("Team", team);
                startActivity(intent);
            }
        });
    }
}
