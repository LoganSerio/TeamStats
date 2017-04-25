package com.example.logan.test2;

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

import java.util.ArrayList;
import java.util.List;

import static com.example.logan.test2.ListPositionsActivity.REQUEST_CODE_ADD_POSITION;

public class ListStatisticsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {

    public static final String TAG = "ListStatisticsActivity";
    public static final int REQUEST_CODE_ADD_STATISTIC = 40;
    private ListView mListViewStatistics;
    private TextView mTxtEmptyListStatistics;
    private ImageButton mBtnAddStatistic;
    private Button mBtnBack;
    private Button mBtnFinish;

    private ListStatisticsAdapter mAdapter;
    private List<Statistic> mListStatistics;
    private StatisticDAO mStatisticDao;
    long positionId;
    Position position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_statistics);

        // initialize the values
        initViews();

        // get the position id
        mStatisticDao = new StatisticDAO(this);
        position = (Position) getIntent().getSerializableExtra("Position");
        positionId = position.getId();
        mAdapter = new ListStatisticsAdapter(this, 0, mListStatistics);
        if (mAdapter != null) {
            mListStatistics = mStatisticDao.getStatisticsOfPosition(positionId); //moved this from on create
            mAdapter = new ListStatisticsAdapter(this, R.layout.list_item_statistic, mListStatistics); //^^same
            mListViewStatistics.setAdapter(mAdapter); // ^^same
            mAdapter.setItems(mListStatistics); //adds the positions to the listview
            mAdapter.notifyDataSetChanged(); //updates listview
        }
    }

    private void initViews() {
        this.mListViewStatistics = (ListView) findViewById(R.id.list_statistics);
        this.mTxtEmptyListStatistics = (TextView) findViewById(R.id.txt_empty_list_statistics);
        this.mBtnAddStatistic = (ImageButton) findViewById(R.id.btn_add_statistic);
        this.mListViewStatistics.setOnItemLongClickListener(this);
        this.mBtnAddStatistic.setOnClickListener(this);
        this.mBtnBack = (Button) findViewById(R.id.btn_back);
        this.mBtnFinish = (Button) findViewById(R.id.btn_finish);
        this.mBtnBack.setOnClickListener(this);
        this.mBtnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_statistic:
                Intent intent = new Intent(this, AddStatisticsActivity.class);
                intent.putExtra("Position", position);
                startActivityForResult(intent, REQUEST_CODE_ADD_POSITION); //sends request code to addpositions
                break;
            case R.id.btn_back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_finish:
                showFinishDialogConfirmation();
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_STATISTIC) {
            if (resultCode == RESULT_OK) {
                //refresh the listView
                if (mListStatistics == null || !mListStatistics.isEmpty()) {
                    mListStatistics = new ArrayList<Statistic>();
                }
                if (mStatisticDao == null)
                    mStatisticDao = new StatisticDAO(this);
                mListStatistics = mStatisticDao.getStatisticsOfPosition(positionId);
                // if the adapter is null, instantiate it
                if (mAdapter == null) {
                    mAdapter = new ListStatisticsAdapter(this, 0, mListStatistics);
                    mListViewStatistics.setAdapter(mAdapter);
                    if (mListViewStatistics.getVisibility() != View.VISIBLE) {
                        mTxtEmptyListStatistics.setVisibility(View.GONE);
                        mListViewStatistics.setVisibility(View.VISIBLE);
                    }
                } else {
                    mListStatistics = mStatisticDao.getStatisticsOfPosition(positionId); //moved this from on create
                    mAdapter = new ListStatisticsAdapter(this, R.layout.list_item_statistic, mListStatistics); //^^same
                    mListViewStatistics.setAdapter(mAdapter); // ^^same
                    mAdapter.setItems(mListStatistics); //adds the positions to the listview
                    mAdapter.notifyDataSetChanged(); //updates listview
                }
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatisticDao.close();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int index, long id) {
        Statistic clickedStatistic = mAdapter.getItem(index);
        Log.d(TAG, "longClickedItem : " + clickedStatistic.getStatisticName());
        showDeleteDialogConfirmation(clickedStatistic);
        return true;
    }

    private void showDeleteDialogConfirmation(final Statistic statistic) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //Set the title and message of the delete confirmation alert
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the statistic \""
                        + statistic.getStatisticName() + " "
                        + "\"");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the position and refresh the list
                if (mStatisticDao != null) {
                    mStatisticDao.deleteStatistic(statistic);

                    //refresh the listView
                    mListStatistics.remove(statistic);
                    if (mListStatistics.isEmpty()) {
                        mListViewStatistics.setVisibility(View.GONE);
                        mTxtEmptyListStatistics.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListStatistics);
                    mAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
                Toast.makeText(ListStatisticsActivity.this, "Statistic deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });
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

    private void showFinishDialogConfirmation() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //Set the title and message of the delete confirmation alert
        alertDialogBuilder.setTitle("Finish");
        alertDialogBuilder.setMessage("This action will save your team and send you back to the homepage. Are you sure you want to continue?");

        // set positive button YES message
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