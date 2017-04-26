package com.example.logan.test2.com.android.teamstats.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Statistic;
import com.example.logan.test2.com.android.teamstats.Database.PositionDAO;
import com.example.logan.test2.com.android.teamstats.Database.StatisticDAO;

/**
 * Adds statistics to the statistic list within a specific position and the database
 */
public class AddStatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddStatisticActivity";
    private EditText txtStatisticName;
    private Button btnAdd;
    Position position;
    long positionId;
    public PositionDAO positionDAO;
    private StatisticDAO statisticDAO;

    /**
     * Puts everything into the activity
     * @param savedInstanceState Instance of the state of the application, if any change it will be updated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statistics);
        this.positionDAO = new PositionDAO(this);
        this.statisticDAO = new StatisticDAO(this);
        this.txtStatisticName = (EditText) findViewById(R.id.txt_statistic_name);
        this.btnAdd = (Button) findViewById(R.id.btn_add);
        this.btnAdd.setOnClickListener(this);
    }

    /**
     * When add is clicked, the created statisitc is added to the database and the statistics listview
     * witihin a specific position
     * @param v a view
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            Editable statisticName = txtStatisticName.getText();
            if (!TextUtils.isEmpty(statisticName)) {
                // add statistic to database
                position = (Position) getIntent().getSerializableExtra("Position");
                positionId = position.getId();
                Statistic createdStatistic = statisticDAO.createStatistic(statisticName.toString(), "0", positionId);
                Log.d(TAG, "added statistic : " + createdStatistic.getStatisticName());
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG);
            }
        }
    }

    /**
     * Cleans app up before moving on to another activity
     */
    protected void onDestroy() {
        super.onDestroy();
    }
}
