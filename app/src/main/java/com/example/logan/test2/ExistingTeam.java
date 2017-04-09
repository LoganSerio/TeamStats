package com.example.logan.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ExistingTeam extends AppCompatActivity {
    ListView teamsListView;
    String[] existingTeams = {"LSU Tigers"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_team);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,existingTeams);
        ListView teamsListView = (ListView) findViewById(R.id.existingTeamListView);
        teamsListView.setAdapter(adapter);
    }
}
