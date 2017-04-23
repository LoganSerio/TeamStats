package com.example.logan.test2;

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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Lists all the teams that the user has created. The user can also hold down a team and then delete it that way.
 */
public class ListTeamsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public static final String TAG = "ListTeamsActivity";

    public static final int REQUEST_CODE_ADD_TEAM = 40;
    public static final String EXTRA_ADDED_TEAM = "extra_key_added_team";

    private ListView mListviewTeams;
    private TextView mTxtEmptyListTeams;

    private ListTeamsAdapter mAdapter;
    private List<Team> mListTeams;
    private TeamDAO mTeamDao;

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
        mTeamDao = new TeamDAO(this);
        mListTeams = mTeamDao.getAllTeams();
        if (mListTeams != null && !mListTeams.isEmpty()) {
            mAdapter = new ListTeamsAdapter(this, mListTeams);
            mListviewTeams.setAdapter(mAdapter);
        } else {
            mTxtEmptyListTeams.setVisibility(View.VISIBLE);
            mListviewTeams.setVisibility(View.GONE);
        }
    }

    /**
     * Initializes the components of the class.
     */
    private void initViews() {
        this.mListviewTeams = (ListView) findViewById(R.id.list_teams);
        this.mTxtEmptyListTeams = (TextView) findViewById(R.id.txt_empty_list_teams);
        this.mListviewTeams.setOnItemClickListener(this);
        this.mListviewTeams.setOnItemLongClickListener(this);
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
                        if (mListTeams == null)
                            mListTeams = new ArrayList<Team>();
                        mListTeams.add(createdTeam);

                        if (mAdapter == null) {
                            if (mListviewTeams.getVisibility() != View.VISIBLE) {
                                mListviewTeams.setVisibility(View.VISIBLE);
                                mTxtEmptyListTeams.setVisibility(View.GONE);
                            }

                            mAdapter = new ListTeamsAdapter(this, mListTeams);
                            mListviewTeams.setAdapter(mAdapter);
                        } else {
                            mAdapter.setItems(mListTeams);
                            mAdapter.notifyDataSetChanged();
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
        mTeamDao.close();
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
        Team clickedTeam = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedTeam.getName());
        TreeMap<String,TreeMap<String,String>> map = getTeamData(clickedTeam.getId());
        Intent intent = new Intent(this, test.class);
        intent.putExtra("Team Data",map);
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
        Team clickedTeam = mAdapter.getItem(position);
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
        alertDialogBuilder.setMessage("Are you sure you want to delete the \"" + clickedTeam.getName() + "\" company ?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the company and refresh the list
                if (mTeamDao != null) {
                    mTeamDao.deleteTeam(clickedTeam);
                    mListTeams.remove(clickedTeam);

                    //refresh the listView
                    if (mListTeams.isEmpty()) {
                        mListviewTeams.setVisibility(View.GONE);
                        mTxtEmptyListTeams.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListTeams);
                    mAdapter.notifyDataSetChanged();
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
    private TreeMap<String, TreeMap<String,String>> getTeamData(long teamId) {
        TreeMap<String, TreeMap<String,String>> positions = new TreeMap<>();
        List<Position> list = new PositionDAO(this).getPositionsOfTeam(teamId);
        for (Position pos : list) {
            List<Statistic> stats = new StatisticDAO(this).getStatisticsOfPosition(pos.getId());
            TreeMap<String,String> savedStats = new TreeMap<>();
            for (Statistic stat : stats) {
                savedStats.put(stat.getStatisticName(),"0");
            }
            positions.put(pos.getPositionName(),savedStats);
        }
        return positions;
    }
}