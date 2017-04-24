package com.example.logan.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddStatisticActivity";
    private EditText mTxtStatisticName;
    private Button mBtnAdd;
    Position position;
    long positionId;

    private PositionDAO mPositionDAO;
    private StatisticDAO mStatisticDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statistics);

        this.mPositionDAO = new PositionDAO(this);
        this.mStatisticDAO = new StatisticDAO(this);

        initViews();
    }

    private void initViews() {
        this.mTxtStatisticName = (EditText) findViewById(R.id.txt_statistic_name);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);
        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_add:
                Editable statisticName = mTxtStatisticName.getText();
                //String value = "0";
                if(!TextUtils.isEmpty(statisticName)) {
                    // add statistic to database
                    position = (Position) getIntent().getSerializableExtra("Position");
                    positionId = position.getId();
                    Statistic createdStatistic = mStatisticDAO.createStatistic(statisticName.toString(), "0", positionId);
                    Log.d(TAG, "added statistic : " + createdStatistic.getStatisticName());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG);
                }
                break;
            default:
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
