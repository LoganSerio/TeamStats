package com.example.logan.test2.com.android.teamstats.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logan.test2.com.android.teamstats.Adapter.ListTeamsAdapter;
import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Database.TeamDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the teams that the user has created. The user can also hold down a team and then delete it that way.
 */
public class ListTeamsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public static final String TAG = "ListTeamsActivity";

    public static final int REQUEST_CODE_ADD_TEAM = 40;
    public static final String EXTRA_ADDED_TEAM = "extra_key_added_team";

    private ListView listviewTeams;
    private TextView txtEmptyListTeams;

    private ListTeamsAdapter adapter;
    private List<Team> listTeams;
    private TeamDAO teamDao;

    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teams);
        // initialize views
        initViews();

        // fill the listView
        teamDao = new TeamDAO(this);
        listTeams = teamDao.getAllTeams();
        if (listTeams != null && !listTeams.isEmpty()) {
            adapter = new ListTeamsAdapter(this, listTeams);
            listviewTeams.setAdapter(adapter);
        } else {
            txtEmptyListTeams.setVisibility(View.VISIBLE);
            listviewTeams.setVisibility(View.GONE);
        }
    }

    /**
     * Initializes the components of the class.
     */
    private void initViews() {
        this.listviewTeams = (ListView) findViewById(R.id.list_teams);
        this.txtEmptyListTeams = (TextView) findViewById(R.id.txt_empty_list_teams);
        this.listviewTeams.setOnItemClickListener(this);
        this.listviewTeams.setOnItemLongClickListener(this);
    }

    @Override
    /**
     * Calls when a user activity is terminated.
     * @param requestCode The integer request code originally supplied that allows you to trace back where the response comes from.
     * @param resultCode The integer result code returned by the child activity through its setResult().
     * @param data An Intent, which can return result data to the caller
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_TEAM) {
            if (resultCode == RESULT_OK) {
                // add the added team to the listTeam and refresh the listView
                if (data != null) {
                    Team createdTeam = (Team) data.getSerializableExtra(EXTRA_ADDED_TEAM);
                    if (createdTeam != null) {
                        if (listTeams == null)
                            listTeams = new ArrayList<Team>();
                        listTeams.add(createdTeam);
                        if (adapter == null) {
                            if (listviewTeams.getVisibility() != View.VISIBLE) {
                                listviewTeams.setVisibility(View.VISIBLE);
                                txtEmptyListTeams.setVisibility(View.GONE);
                            }
                            adapter = new ListTeamsAdapter(this, listTeams);
                            listviewTeams.setAdapter(adapter);
                        } else {
                            adapter.setItems(listTeams);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    /**
     * Closes the database.
     */
    protected void onDestroy() {
        super.onDestroy();
        teamDao.close();
    }

    @Override
    /**
     * Allows for the user to interact and select a team from the view.
     * @param parent The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     * */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Team clickedTeam = adapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedTeam.getName());
        Intent intent = new Intent(this, TeamPageActivity.class);
        intent.putExtra("Team",clickedTeam);
        startActivity(intent);
    }

    @Override
    /**
     * In the event the user holds the team name down, the user has the option to delete the team.
     * @param parent The AbsListView where the click happened.
     * @param view The view within the AbsListView that was clicked.
     * @param position The position of the view in the list.
     * @param id The row id of the item that was clicked.
     * @return returns true is the click was held or false otherwise.
     */
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Team clickedTeam = adapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedTeam.getName());
        showDeleteDialogConfirmation(clickedTeam);
        return true;
    }

    /**
     * Displays a message asking the user for confirmation if they wish to delete the team.
     * @param clickedTeam The name of the deleted team.
     */
    private void showDeleteDialogConfirmation(final Team clickedTeam) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete \"" + clickedTeam.getName() + "\"  ?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (teamDao != null) {
                    teamDao.deleteTeam(clickedTeam);
                    listTeams.remove(clickedTeam);

                    //refresh the listView
                    if (listTeams.isEmpty()) {
                        listviewTeams.setVisibility(View.GONE);
                        txtEmptyListTeams.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(listTeams);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
                Toast.makeText(ListTeamsActivity.this, "Team deleted successfully", Toast.LENGTH_SHORT).show();
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