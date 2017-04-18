package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A class that creates the activity for Creating a team
 */
public class CreateTeamPopUp extends AppCompatActivity {
    Button btnCloseTeamPopUp;
    Button btnCreateTeamPopUp;
    EditText etTeamName;

    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team_pop_up);

        btnCloseTeamPopUp =(Button)findViewById(R.id.cancelTeamCreateButton);
        btnCreateTeamPopUp = (Button)findViewById(R.id.teamCreatePopUpButton);
        btnCloseTeamPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCreateTeamPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTeamName = (EditText) findViewById(R.id.EnterTeamNameEditText);
                if(TextUtils.isEmpty(etTeamName.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Must enter team name",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent myIntent = new Intent(CreateTeamPopUp.this, AddPosition.class);
                    startActivity(myIntent);
                }

            }
        });
    }
    public void writeData(View view) {
        String teamName = etTeamName.getText().toString();
        String fileName = new String (teamName);
        try {
            FileOutputStream fileOS = openFileOutput(fileName,MODE_PRIVATE);
            fileOS.write(teamName.getBytes());
            fileOS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
