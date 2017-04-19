package com.example.logan.test2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class ListTeamsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    public static final String TAG = "ListTeamsActivity";

    public static final int REQUEST_CODE_ADD_TEAM = 40;
    public static final String EXTRA_ADDED_TEAM = "extra_key_added_team";

    private ListView mListviewTeams;
    private TextView mTxtEmptyListTeams;
    private ImageButton mBtnAddTeam;

    private ListTeamsAdapter mAdapter;
    private List<Team> mListTeams;
    private TeamDAO mTeamDao;

    @Override
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

    private void initViews() {
        this.mListviewTeams = (ListView) findViewById(R.id.list_teams);
        this.mTxtEmptyListTeams = (TextView) findViewById(R.id.txt_empty_list_teams);
        this.mBtnAddTeam = (ImageButton) findViewById(R.id.btn_add_team);
        this.mListviewTeams.setOnItemClickListener(this);
        this.mListviewTeams.setOnItemLongClickListener(this);
        this.mBtnAddTeam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_team:
                Intent intent = new Intent(this, CreateTeamPopUp.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_TEAM);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_TEAM) {
            if (resultCode == RESULT_OK) {
                // add the added team to the listCompanies and refresh the listView
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
    protected void onDestroy() {
        super.onDestroy();
        mTeamDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Team clickedTeam = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : " + clickedTeam.getName());
        Intent intent = new Intent(this, ListPositionsActivity.class);
        intent.putExtra(ListPositionsActivity.EXTRA_SELECTED_TEAM_ID, clickedTeam.getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Team clickedTeam = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedTeam.getName());
        showDeleteDialogConfirmation(clickedTeam);
        return true;
    }

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
}