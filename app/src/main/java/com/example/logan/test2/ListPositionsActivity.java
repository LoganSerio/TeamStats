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


public class ListPositionsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "ListPositionsActivity";

    public static final int REQUEST_CODE_ADD_POSITION = 40;
    public static final int REQUEST_CODE_STATS_LIST = 69;
    public static final String EXTRA_ADDED_POSITION = "extra_key_added_employee";
    public static final String EXTRA_SELECTED_TEAM_ID = "extra_key_selected_team_id";

    private ListView mListviewPositions;
    private TextView mTxtEmptyListPositions;
    private ImageButton mBtnAddPosition;

    private ListPositionsAdapter mAdapter;
    private List<Position> mListPositions;
    private PositionDAO mPositionDao;
    long teamId;
    Team team;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_positions);

        // initialize views
        initViews();

        // get the team id
        mPositionDao = new PositionDAO(this);


        team = (Team) getIntent().getSerializableExtra("Team");
        teamId = team.getId();

        mAdapter = new ListPositionsAdapter(this, 0, mListPositions);
        if (mAdapter != null) {
            mListPositions = mPositionDao.getPositionsOfTeam(teamId); //moved this from on create
            mAdapter = new ListPositionsAdapter(this, R.layout.list_item_position, mListPositions); //^^same
            mListviewPositions.setAdapter(mAdapter); // ^^same
            mAdapter.setItems(mListPositions); //adds the positions to the listview
            mAdapter.notifyDataSetChanged(); //updates listview
        }
        //mListPositions = mPositionDao.getPositionsOfTeam(teamId);
        //mAdapter = new ListPositionsAdapter(this, R.layout.list_item_position, mListPositions);

        //mListviewPositions.setAdapter(mAdapter);

    }

    private void initViews() {
        this.mListviewPositions = (ListView) findViewById(R.id.list_positions);
        this.mTxtEmptyListPositions = (TextView) findViewById(R.id.txt_empty_list_positions);
        this.mBtnAddPosition = (ImageButton) findViewById(R.id.btn_add_position);
        this.mListviewPositions.setOnItemClickListener(this);
        this.mListviewPositions.setOnItemLongClickListener(this);
        this.mBtnAddPosition.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_position:
                Intent intent = new Intent(this, AddPositionActivity.class);
                intent.putExtra("Team", team);
                startActivityForResult(intent, REQUEST_CODE_ADD_POSITION); //sends request code to addpositions
                break;

            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_POSITION) {
            if(resultCode == RESULT_OK) {
                //refresh the listView
                if(mListPositions == null || !mListPositions.isEmpty()) {
                    mListPositions = new ArrayList<Position>();
                }

                if(mPositionDao == null)
                    mPositionDao = new PositionDAO(this);
                mListPositions = mPositionDao.getPositionsOfTeam(teamId);
                if(mAdapter == null) {
                    mAdapter = new ListPositionsAdapter(this, 0,mListPositions);
                    mListviewPositions.setAdapter(mAdapter);
                    if(mListviewPositions.getVisibility() != View.VISIBLE) {
                        mTxtEmptyListPositions.setVisibility(View.GONE);
                        mListviewPositions.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    mListPositions = mPositionDao.getPositionsOfTeam(teamId); //moved this from on create
                    mAdapter = new ListPositionsAdapter(this, R.layout.list_item_position, mListPositions); //^^same
                    mListviewPositions.setAdapter(mAdapter); // ^^same
                    mAdapter.setItems(mListPositions); //adds the positions to the listview
                    mAdapter.notifyDataSetChanged(); //updates listview
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPositionDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Position clickedPosition = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedPosition.getPositionName());
        Intent intent = new Intent(this, ListStatisticsActivity.class);
        intent.putExtra("Position", clickedPosition);
        startActivityForResult(intent,REQUEST_CODE_STATS_LIST);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Position clickedPosition = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : "+clickedPosition.getPositionName());

        showDeleteDialogConfirmation(clickedPosition);
        return true;
    }

    private void showDeleteDialogConfirmation(final Position position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the position \""
                        + position.getPositionName() + " "
                        + "\"");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the position and refresh the list
                if(mPositionDao != null) {
                    mPositionDao.deletePosition(position);

                    //refresh the listView
                    mListPositions.remove(position);
                    if(mListPositions.isEmpty()) {
                        mListviewPositions.setVisibility(View.GONE);
                        mTxtEmptyListPositions.setVisibility(View.VISIBLE);
                    }

                    mAdapter.setItems(mListPositions);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListPositionsActivity.this, "Position deleted successfully", Toast.LENGTH_SHORT).show();

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
