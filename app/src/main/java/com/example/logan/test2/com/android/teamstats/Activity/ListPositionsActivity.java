package com.example.logan.test2.com.android.teamstats.Activity;

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

import com.example.logan.test2.com.android.teamstats.Adapter.ListPositionsAdapter;
import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Database.PositionDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that lists all the created positions of a team, none listed if no positions have been created
 */
public class ListPositionsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "ListPositionsActivity";
    public static final int REQUEST_CODE_ADD_POSITION = 40;
    public static final int REQUEST_CODE_STATS_LIST = 69;
    private ListView listviewPositions;
    private TextView txtEmptyListPositions;
    private ImageButton btnAddPosition;
    private ListPositionsAdapter adapter;
    private List<Position> listPositions;
    private PositionDAO positionDao;
    long teamId;
    Team team;

    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app in case the app needs to be re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_positions);
        this.listviewPositions = (ListView) findViewById(R.id.list_positions);
        this.txtEmptyListPositions = (TextView) findViewById(R.id.txt_empty_list_positions);
        this.btnAddPosition = (ImageButton) findViewById(R.id.btn_add_position);
        this.listviewPositions.setOnItemClickListener(this);
        this.listviewPositions.setOnItemLongClickListener(this);
        this.btnAddPosition.setOnClickListener(this);
        positionDao = new PositionDAO(this);
        team = (Team) getIntent().getSerializableExtra("Team");
        teamId = team.getId();
        adapter = new ListPositionsAdapter(this, 0, listPositions);
        if (adapter != null) {
            listPositions = positionDao.getPositionsOfTeam(teamId);
            adapter = new ListPositionsAdapter(this, R.layout.list_item_position, listPositions);
            listviewPositions.setAdapter(adapter);
            adapter.setItems(listPositions);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * When the plus button in the top right of the list position activity is clicked,
     * the user is sent to the add position activity
     * @param v a view
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_position)
        {
            Intent intent = new Intent(this, AddPositionActivity.class);
            intent.putExtra("Team", team);
            startActivityForResult(intent, REQUEST_CODE_ADD_POSITION);
        }
    }

    /**
     * Starts a new activity and expects to get the results of the activity back
     * @param requestCode identify from which Intent you came back
     * @param resultCode code that the activity that it starts from returnz
     * @param data data from intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_POSITION) {
            if(resultCode == RESULT_OK) {
                if(listPositions == null || !listPositions.isEmpty()) {
                    listPositions = new ArrayList<Position>();
                }
                if(positionDao == null)
                    positionDao = new PositionDAO(this);
                listPositions = positionDao.getPositionsOfTeam(teamId);
                if(adapter == null) {
                    adapter = new ListPositionsAdapter(this, 0, listPositions);
                    listviewPositions.setAdapter(adapter);
                    if(listviewPositions.getVisibility() != View.VISIBLE) {
                        txtEmptyListPositions.setVisibility(View.GONE);
                        listviewPositions.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    listPositions = positionDao.getPositionsOfTeam(teamId);
                    adapter = new ListPositionsAdapter(this, R.layout.list_item_position, listPositions);
                    listviewPositions.setAdapter(adapter);
                    adapter.setItems(listPositions);
                    adapter.notifyDataSetChanged();
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Cleans app up before moving on to another activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        positionDao.close();
    }

    /**
     * When an item is clicked, the user is brought to a page where they can add statistics to the
     * clicked position
     * @param parent where the click happened
     * @param view a view
     * @param index the index of the position that was clicked
     * @param id the position id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        Position clickedPosition = adapter.getItem(index);
        Log.d(TAG, "clickedItem : "+clickedPosition.getPositionName());
        Intent intent = new Intent(this, ListStatisticsActivity.class);
        intent.putExtra("Position", clickedPosition);
        startActivityForResult(intent,REQUEST_CODE_STATS_LIST);
    }

    /**
     * When an item is long clicked, they see the delete dialog pop up asking if they want to delete
     * a position
     * @param parent where the long click happed
     * @param view a view
     * @param index the index of the position that was clicked
     * @param id the position id
     * @return true if the position was deleted
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int index, long id) {
        Position clickedPosition = adapter.getItem(index);
        Log.d(TAG, "longClickedItem : "+clickedPosition.getPositionName());
        showDeleteDialogConfirmation(clickedPosition);
        return true;
    }

    /**
     * A dialog that shows up if a position is long clicked and deletes it if yes is clicked
     * @param position a position
     */
    private void showDeleteDialogConfirmation(final Position position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the position \""
                + position.getPositionName() + " " + "\"");
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(positionDao != null) {
                    positionDao.deletePosition(position);
                    listPositions.remove(position);
                    if(listPositions.isEmpty()) {
                        listviewPositions.setVisibility(View.GONE);
                        txtEmptyListPositions.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(listPositions);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
                Toast.makeText(ListPositionsActivity.this, "Position deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
