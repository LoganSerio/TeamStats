package com.example.logan.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A that deals with the existing teams in the app.
 */
public class ExistingTeam extends AppCompatActivity {
    ListView teamsListView;
    String[] existingTeams = {"LSU Tigers"};
    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_team);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,existingTeams);
        teamsListView = (ListView) findViewById(R.id.existingTeamListView);
        teamsListView.setAdapter(adapter);
    }
}
