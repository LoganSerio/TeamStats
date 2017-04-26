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
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Database.PositionDAO;
import com.example.logan.test2.com.android.teamstats.Database.TeamDAO;

/**
 * Adds positions to the position list and the database
 */
public class AddPositionActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddPositionActivity";

    private EditText txtPositionName;
    private Button btnAdd;
    private TeamDAO teamDao;
    private PositionDAO positionDao;
    Team team;
    long teamID;

    /**
     * Puts everything into the activity
     * @param savedInstanceState Instance of the state of the application, if any change it will be updated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
        this.teamDao = new TeamDAO(this);
        this.positionDao = new PositionDAO(this);
        this.txtPositionName = (EditText) findViewById(R.id.txt_position_name);
        this.btnAdd = (Button) findViewById(R.id.btn_add);
        this.btnAdd.setOnClickListener(this);
    }

    /**
     * When add is clicked, the created position is added to the database and the listview
     * @param v a view
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add){
            Editable positionName = txtPositionName.getText();
            if (!TextUtils.isEmpty(positionName)) {
                team = (Team) getIntent().getSerializableExtra("Team");
                teamID = team.getId();
                Position createdPosition = positionDao.createPosition(positionName.toString(), teamID);
                Log.d(TAG, "added position : " + createdPosition.getPositionName());
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    /**
     * Cleans app up before moving on to another activity
     */
    protected void onDestroy() {
        super.onDestroy();
        teamDao.close();
    }

}