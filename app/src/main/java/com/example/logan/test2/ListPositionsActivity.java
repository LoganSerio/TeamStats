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

/**
 *
 */
public class ListPositionsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "ListPositionsActivity";

    public static final int REQUEST_CODE_ADD_POSITION = 40;
    public static final String EXTRA_ADDED_POSITION = "extra_key_added_employee";
    public static final String EXTRA_SELECTED_TEAM_ID = "extra_key_selected_team_id";

    private ListView mListviewPositions;
    private TextView mTxtEmptyListPositions;
    private ImageButton mBtnAddPosition;

    private ListPositionsAdapter mAdapter;
    private List<Position> mListPositions;
    private PositionDAO mPositionDao;

    private long mTeamId = -1;

    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_positions);

        // initialize views
        initViews();

        // get the team id from extras
        mPositionDao = new PositionDAO(this);
        Intent intent  = getIntent();
        if(intent != null) {
            this.mTeamId = intent.getLongExtra(EXTRA_SELECTED_TEAM_ID, -1);
        }

        if(mTeamId != -1) {
            mListPositions = mPositionDao.getPositionsOfTeam(mTeamId);
            // fill the listView
            if(mListPositions != null && !mListPositions.isEmpty()) {
                mAdapter = new ListPositionsAdapter(this, mListPositions);
                mListviewPositions.setAdapter(mAdapter);
            }
            else {
                mTxtEmptyListPositions.setVisibility(View.VISIBLE);
                mListviewPositions.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Initializes the variables for this class.
     */
    private void initViews() {
        this.mListviewPositions = (ListView) findViewById(R.id.list_positions);
        this.mTxtEmptyListPositions = (TextView) findViewById(R.id.txt_empty_list_positions);
        this.mBtnAddPosition = (ImageButton) findViewById(R.id.btn_add_position);
        this.mListviewPositions.setOnItemClickListener(this);
        this.mListviewPositions.setOnItemLongClickListener(this);
        this.mBtnAddPosition.setOnClickListener(this);
    }

    @Override
    /**
     * Provides the desired activity in the event of a button click.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_position:
                Intent intent = new Intent(this, AddPositionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_POSITION);
                break;

            default:
                break;
        }
    }

    @Override
    /**
     *
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_POSITION) {
            if(resultCode == RESULT_OK) {
                //refresh the listView
                if(mListPositions == null || !mListPositions.isEmpty()) {
                    mListPositions = new ArrayList<Position>();
                }

                if(mPositionDao == null)
                    mPositionDao = new PositionDAO(this);
                mListPositions = mPositionDao.getPositionsOfTeam(mTeamId);
                if(mAdapter == null) {
                    mAdapter = new ListPositionsAdapter(this, mListPositions);
                    mListviewPositions.setAdapter(mAdapter);
                    if(mListviewPositions.getVisibility() != View.VISIBLE) {
                        mTxtEmptyListPositions.setVisibility(View.GONE);
                        mListviewPositions.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    mAdapter.setItems(mListPositions);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    /**
     * Closes the database.
     */
    protected void onDestroy() {
        super.onDestroy();
        mPositionDao.close();
    }

    @Override
    /**
     * When an item in the ListView
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Position clickedPosition = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedPosition.getPositionName());
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
                .setMessage("Are you sure you want to delete the employee \""
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
