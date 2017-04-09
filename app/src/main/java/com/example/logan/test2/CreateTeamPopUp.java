package com.example.logan.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateTeamPopUp extends AppCompatActivity {
    Button btnCloseTeamPopUp;
    Button btnCreateTeamPopUp;
    EditText etTeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team_pop_up);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));

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
                Intent myIntent = new Intent(CreateTeamPopUp.this,CreatePosition.class);
                startActivity(myIntent);
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
