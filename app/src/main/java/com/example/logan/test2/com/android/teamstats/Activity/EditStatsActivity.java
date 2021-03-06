package com.example.logan.test2.com.android.teamstats.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Statistic;
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Database.PositionDAO;
import com.example.logan.test2.com.android.teamstats.Database.StatisticDAO;

import java.util.ArrayList;

/**
 * A class that creates the activity for editing stats on a specific team's team page
 */
public class EditStatsActivity extends AppCompatActivity {
    Button btnSaveStats;
    public ArrayList<EditText> editList;
    PositionDAO positionDAO;
    StatisticDAO statisticDAO;
    ArrayList<Position> posList;
    ArrayList<Statistic> statList;
    int size = 0;

    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app in case the app needs to be re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stats);
        btnSaveStats = (Button) findViewById(R.id.saveStatsButton);
        btnSaveStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statList = new ArrayList<Statistic>(getStatList(posList));
                for (Statistic stats : statList) {
                    statisticDAO.updateStatistics(stats);
                }
                Intent intent = new Intent(EditStatsActivity.this,TeamPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Team team = (Team) getIntent().getSerializableExtra("Team");
                intent.putExtra("Team",team);
                startActivity(intent);
                finish();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        editList = new ArrayList<EditText>();
        Team team = (Team) getIntent().getSerializableExtra("Team");
        String name = team.getName();
        TextView tvTN = (TextView) findViewById(R.id.tvTeamName);
        tvTN.setText(name);
        tvTN.setTextSize(45);
        tvTN.setTypeface(null,Typeface.BOLD);

        TableLayout tableLayout;
        tableLayout = (TableLayout) findViewById(R.id.tb);
        TableRow tr;
        positionDAO = new PositionDAO(this);
        posList = (ArrayList<Position>) positionDAO.getPositionsOfTeam(team.getId());
        for (Position pos : posList) {
            statisticDAO = new StatisticDAO(this);
            statList = (ArrayList<Statistic>) statisticDAO.getStatisticsOfPosition(pos.getId());
            final TextView tv = new TextView(this);
            TableRow tr1 = new TableRow(this);
            tv.setText(pos.getPositionName());
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
            tv.setTextSize(34);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
            tr1.addView(tv);
            tableLayout.addView(tr1);
            for (Statistic stat : statList) {
                tr = new TableRow(this);
                final TextView tv1 = new TextView(this);
                final EditText tv2 = new EditText(this);
                tv1.setText(stat.getStatisticName()+":");
                tv2.setText(stat.getStatisticValue());
                tv1.setTextSize(25);
                tv2.setTextSize(25);
                tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setGravity(Gravity.CENTER);
                editList.add(tv2);
                tr.addView(tv1);
                tr.addView(tv2);
                tableLayout.addView(tr);
            }
        }
    }

    /**
     * Looks at stats by their correlating position and returns all the user inputted stats
     * @param posOld the positions list
     * @return a new stat list containing the stats inputted by user
     */
    private ArrayList<Statistic> getStatList(ArrayList<Position> posOld) {
        ArrayList<Statistic> statNew = new ArrayList<>();
        for (Position pos : posOld) {
            ArrayList<Statistic> statOld = (ArrayList<Statistic>) statisticDAO.getStatisticsOfPosition(pos.getId());
            for (Statistic stat : statOld) {
                stat.setStatisticValue(editList.get(size).getText().toString());
                statNew.add(stat);
                size++;
            }
        }
        System.out.println(statNew);
        return statNew;
    }
}
