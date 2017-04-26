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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_positions);

        // initialize views
        initViews();

        // get the team id
        positionDao = new PositionDAO(this);

        team = (Team) getIntent().getSerializableExtra("Team");
        teamId = team.getId();

        adapter = new ListPositionsAdapter(this, 0, listPositions);
        if (adapter != null) {
            listPositions = positionDao.getPositionsOfTeam(teamId); //moved this from on create
            adapter = new ListPositionsAdapter(this, R.layout.list_item_position, listPositions); //^^same
            listviewPositions.setAdapter(adapter); // ^^same
            adapter.setItems(listPositions); //adds the positions to the listview
            adapter.notifyDataSetChanged(); //updates listview
        }
    }

    private void initViews() {
        this.listviewPositions = (ListView) findViewById(R.id.list_positions);
        this.txtEmptyListPositions = (TextView) findViewById(R.id.txt_empty_list_positions);
        this.btnAddPosition = (ImageButton) findViewById(R.id.btn_add_position);
        this.listviewPositions.setOnItemClickListener(this);
        this.listviewPositions.setOnItemLongClickListener(this);
        this.btnAddPosition.setOnClickListener(this);
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
                    listPositions = positionDao.getPositionsOfTeam(teamId); //moved this from on create
                    adapter = new ListPositionsAdapter(this, R.layout.list_item_position, listPositions); //^^same
                    listviewPositions.setAdapter(adapter); // ^^same
                    adapter.setItems(listPositions); //adds the positions to the listview
                    adapter.notifyDataSetChanged(); //updates listview
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        positionDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Position clickedPosition = adapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedPosition.getPositionName());
        Intent intent = new Intent(this, ListStatisticsActivity.class);
        intent.putExtra("Position", clickedPosition);
        startActivityForResult(intent,REQUEST_CODE_STATS_LIST);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Position clickedPosition = adapter.getItem(position);
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
                if(positionDao != null) {
                    positionDao.deletePosition(position);

                    //refresh the listView
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
