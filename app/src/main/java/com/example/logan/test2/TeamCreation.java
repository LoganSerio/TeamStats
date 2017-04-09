package com.example.logan.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class TeamCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);
    }
    public void writeData(View view) {

    }
    public void readData(View view) {
        try {
            FileInputStream fileIS = openFileInput("Hello");
            InputStreamReader inputSR = new InputStreamReader(fileIS);
            BufferedReader br = new BufferedReader(inputSR);
            StringBuffer stringBuffer = new StringBuffer();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
