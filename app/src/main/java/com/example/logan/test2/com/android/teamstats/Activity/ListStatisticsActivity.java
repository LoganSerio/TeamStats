package com.example.logan.test2.com.android.teamstats.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logan.test2.com.android.teamstats.Adapter.ListStatisticsAdapter;
import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Statistic;
import com.example.logan.test2.com.android.teamstats.Database.StatisticDAO;

import java.util.ArrayList;
import java.util.List;

import static com.example.logan.test2.com.android.teamstats.Activity.ListPositionsActivity.REQUEST_CODE_ADD_POSITION;

/**
 * A class that lists all the created statistics of a position, none listed if no statistics have been created
 */
public class ListStatisticsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {

    public static final String TAG = "ListStatisticsActivity";
    public static final int REQUEST_CODE_ADD_STATISTIC = 40;
    private ListView listViewStatistics;
    private TextView txtEmptyListStatistics;
    private ImageButton btnAddStatistic;
    private Button btnBack;
    private Button btnFinish;
    private ListStatisticsAdapter adapter;
    private List<Statistic> listStatistics;
    private StatisticDAO statisticDao;
    long positionId;
    Position position;

    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app in case the app needs to be re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_statistics);
        this.listViewStatistics = (ListView) findViewById(R.id.list_statistics);
        this.txtEmptyListStatistics = (TextView) findViewById(R.id.txt_empty_list_statistics);
        this.btnAddStatistic = (ImageButton) findViewById(R.id.btn_add_statistic);
        this.listViewStatistics.setOnItemLongClickListener(this);
        this.btnAddStatistic.setOnClickListener(this);
        this.btnBack = (Button) findViewById(R.id.btn_back);
        this.btnFinish = (Button) findViewById(R.id.btn_finish);
        this.btnBack.setOnClickListener(this);
        this.btnFinish.setOnClickListener(this);
        statisticDao = new StatisticDAO(this);
        position = (Position) getIntent().getSerializableExtra("Position");
        positionId = position.getId();
        adapter = new ListStatisticsAdapter(this, 0, listStatistics);
        if (adapter != null) {
            listStatistics = statisticDao.getStatisticsOfPosition(positionId);
            adapter = new ListStatisticsAdapter(this, R.layout.list_item_statistic, listStatistics);
            listViewStatistics.setAdapter(adapter);
            adapter.setItems(listStatistics);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Adds a statistic to the list view, goes back to the positions list if back is hit and
     * makes the finish creating team pop up appear if the finish button is clicked
     * @param v a view
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_statistic){
            Intent intent = new Intent(this, AddStatisticsActivity.class);
            intent.putExtra("Position", position);
            startActivityForResult(intent, REQUEST_CODE_ADD_POSITION);
        }
        else if(v.getId() == R.id.btn_back) {
            setResult(RESULT_OK);
            finish();
        }
        else
            showFinishDialogConfirmation();
        }

    /**
     * Starts a new activity and expects to get the results of the activity back
     * @param requestCode identify from which Intent you came back
     * @param resultCode code that the activity that it starts from returnz
     * @param data data from intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_STATISTIC) {
            if (resultCode == RESULT_OK) {
                if (listStatistics == null || !listStatistics.isEmpty()) {
                    listStatistics = new ArrayList<Statistic>();
                }
                if (statisticDao == null)
                    statisticDao = new StatisticDAO(this);
                listStatistics = statisticDao.getStatisticsOfPosition(positionId);
                if (adapter == null) {
                    adapter = new ListStatisticsAdapter(this, 0, listStatistics);
                    listViewStatistics.setAdapter(adapter);
                    if (listViewStatistics.getVisibility() != View.VISIBLE) {
                        txtEmptyListStatistics.setVisibility(View.GONE);
                        listViewStatistics.setVisibility(View.VISIBLE);
                    }
                } else {
                    listStatistics = statisticDao.getStatisticsOfPosition(positionId);
                    adapter = new ListStatisticsAdapter(this, R.layout.list_item_statistic, listStatistics);
                    listViewStatistics.setAdapter(adapter);
                    adapter.setItems(listStatistics);
                    adapter.notifyDataSetChanged();
                }
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Cleans app up before moving on to another activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        statisticDao.close();
    }

    /**
     * When an item is long clicked, they see the delete dialog pop up asking if they want to delete
     * a stat
     * @param parent where the long click happened
     * @param view a view
     * @param index the index of the stat that was clicked
     * @param id the stat id
     * @return true if the stat was deleted
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int index, long id) {
        Statistic clickedStatistic = adapter.getItem(index);
        Log.d(TAG, "longClickedItem : " + clickedStatistic.getStatisticName());
        showDeleteDialogConfirmation(clickedStatistic);
        return true;
    }

    /**
     * A dialog that shows up if a position is long clicked and deletes it if yes is clicked
     * @param statistic a stat
     */
    private void showDeleteDialogConfirmation(final Statistic statistic) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the statistic \""
                + statistic.getStatisticName() + " " + "\"");
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (statisticDao != null) {
                    statisticDao.deleteStatistic(statistic);
                    listStatistics.remove(statistic);
                    if (listStatistics.isEmpty()) {
                        listViewStatistics.setVisibility(View.GONE);
                        txtEmptyListStatistics.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(listStatistics);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
                Toast.makeText(ListStatisticsActivity.this, "Statistic deleted successfully", Toast.LENGTH_SHORT).show();
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

    /**
     * Appears if the finish button is clicked, asking the user if they are done creating their team
     */
    private void showFinishDialogConfirmation() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Finish");
        alertDialogBuilder.setMessage("This action will save your team and send you back to the homepage. Are you sure you want to continue?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ListStatisticsActivity.this, HomepageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface alertDialogBuilder, int id) {
                        alertDialogBuilder.cancel();
                    }
                });
        alertDialogBuilder.show();
    }
}