package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPositionActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddPositionActivity";

    private EditText mTxtPositionName;
    private Button mBtnAdd;
    private ListTeamsAdapter mListAdapter;

    private TeamDAO mTeamDao;
    private PositionDAO mPositionDao;
    Team team;
    long teamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
    /*
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPositionActivity.this, ListPositionsActivity.class));
            }
        });
    */
        this.mTeamDao = new TeamDAO(this);
        this.mPositionDao = new PositionDAO(this);

        //teamID = team.getId();
        //ArrayList<Position> listPositions = new ArrayList<>(mPositionDao.getAllPositions());

        initViews();

    }

    private void initViews() {
        this.mTxtPositionName = (EditText) findViewById(R.id.txt_position_name);
        //this.mSpinnerTeam = (Spinner) findViewById(R.id.spinner_teams);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable positionName = mTxtPositionName.getText();
                //mSelectedTeam = (Team) mSpinnerTeam.getSelectedItem();
                if (!TextUtils.isEmpty(positionName)) {
                    // add the team to database
                    team = (Team) getIntent().getSerializableExtra("Team");
                    teamID = team.getId();
                    Position createdPosition = mPositionDao.createPosition(positionName.toString(), teamID);
                    Log.d(TAG, "added position : " + createdPosition.getPositionName());
                    setResult(RESULT_OK);
                    finish();

                    Intent intent = new Intent(AddPositionActivity.this,ListPositionsActivity.class);
                    intent.putExtra("Team",team);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTeamDao.close();
    }

}