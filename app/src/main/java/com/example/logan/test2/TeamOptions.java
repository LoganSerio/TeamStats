package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TeamOptions extends Fragment{

    Button btnEditPositions;
    Button btnEditStatistics;
    Button btnEditPlayers;
    Button btnDeleteTeam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_options, container, false);
        btnEditPositions = (Button) rootView.findViewById(R.id.editPositionButton);
        btnEditStatistics = (Button) rootView.findViewById(R.id.editStatsButton);
        btnEditPlayers = (Button) rootView.findViewById(R.id.editPlayerButton);
        btnDeleteTeam = (Button) rootView.findViewById(R.id.deleteTeamButton);

        btnEditPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeamOptions.this.getActivity(),ListPositionsActivity.class));
            }
        });

        btnEditStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEditPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDeleteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }
}
