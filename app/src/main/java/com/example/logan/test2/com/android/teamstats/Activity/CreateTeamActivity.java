package com.example.logan.test2.com.android.teamstats.Activity;

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

import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Database.TeamDAO;

/**
 * A class that creates the activity for Creating a team
 */
public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "CreateTeamActivity";
    private EditText txtTeamName;
    private Button btnAdd;
    private TeamDAO teamDao;

    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        initViews();

        this.teamDao = new TeamDAO(this);
    }

    /**
     * Initializes components on the activity page.
     */
    private void initViews() {
        this.txtTeamName = (EditText) findViewById(R.id.txt_team_name);
        this.btnAdd = (Button) findViewById(R.id.btn_add);
        this.btnAdd.setOnClickListener(this);
    }

    @Override
    /**
     * Tells the button what to do in the event of a click.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable teamName = txtTeamName.getText();
                if (!TextUtils.isEmpty(teamName)) {
                    Team createdTeam = teamDao.createTeam(teamName.toString());
                    Log.d(TAG, "added team : "+ createdTeam.getName());
                    Intent intent = new Intent(CreateTeamActivity.this,ListPositionsActivity.class);
                    intent.putExtra("Team", createdTeam);
                    startActivity(intent);

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    /**
     * Closes the database.
     */
    protected void onDestroy() {
        super.onDestroy();
        teamDao.close();
    }
}
