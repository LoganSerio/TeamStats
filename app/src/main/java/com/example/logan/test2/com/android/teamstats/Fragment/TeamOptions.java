package com.example.logan.test2.com.android.teamstats.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Activity.EditStatsActivity;
import com.example.logan.test2.com.android.teamstats.Activity.HomepageActivity;
import com.example.logan.test2.com.android.teamstats.Activity.ListPositionsActivity;
import com.example.logan.test2.com.android.teamstats.Database.TeamDAO;


/**
 * The page that deals with a users ability to update a team.
 */
public class TeamOptions extends Fragment{

    Button btnEditPositions;
    Button btnEditStatistics;
    Button btnDeleteTeam;
    TeamDAO teamDAO;

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
        teamDAO = new TeamDAO(getActivity());

        btnEditPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Team team = (Team) getActivity().getIntent().getSerializableExtra("Team");
                Intent intent = new Intent(TeamOptions.this.getActivity(),ListPositionsActivity.class);
                intent.putExtra("Team",team);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnEditStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Team team = (Team) getActivity().getIntent().getSerializableExtra("Team");
                Intent intent = new Intent(TeamOptions.this.getActivity(),EditStatsActivity.class);
                intent.putExtra("Team",team);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnDeleteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Team team = (Team) getActivity().getIntent().getSerializableExtra("Team");
                showDeleteDialogConfirmation(team);
            }
        });

        return rootView;
    }
    /**
     * Displays a message asking the user for confirmation if they wish to delete the team.
     * @param team The name of the deleted team.
     */
    private void showDeleteDialogConfirmation(final Team team) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete \"" + team.getName() + "\"  ?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the team and refresh the list
                teamDAO.deleteTeam(team);
                dialog.dismiss();
                Toast.makeText(TeamOptions.this.getActivity(), "Team deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeamOptions.this.getActivity(),HomepageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

}
