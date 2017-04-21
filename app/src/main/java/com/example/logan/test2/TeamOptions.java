package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * The page that deals with a users ability to update a team.
 */
public class TeamOptions extends Fragment{

    Button btnEditPositions;
    Button btnEditStatistics;
    Button btnDeleteTeam;

    @Override
    /**
     * Allows for user interactivity with the team options page.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container The container where the fragment UI is attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return an instance of View.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_options, container, false);
        btnEditPositions = (Button) rootView.findViewById(R.id.editPositionButton);
        btnEditStatistics = (Button) rootView.findViewById(R.id.editStatsButton);
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

        btnDeleteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }
}
