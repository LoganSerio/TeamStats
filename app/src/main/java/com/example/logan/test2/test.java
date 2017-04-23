package com.example.logan.test2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

public class test extends AppCompatActivity {
    public TreeMap<String, TreeMap<String,String>> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        map = new TreeMap<>((Map<String, TreeMap<String,String>>) getIntent().getSerializableExtra("Team Data"));
        TableLayout tableLayout;
        tableLayout = (TableLayout) findViewById(R.id.tb);
        TableRow tr;
        for (Map.Entry<String,TreeMap<String,String>> pos : map.entrySet()) {
            TreeMap<String,String> stats = new TreeMap<String, String> ((Map<String,String>) (pos.getValue()));
            tr = new TableRow(this);
            final TextView tv = new TextView(this);
            //Team team = (Team) getIntent().getSerializableExtra("Team");
            //tv.setText(team.getName());
            //tv.setTypeface(null, Typeface.BOLD_ITALIC);
            //tv.setTextSize(30);
            //tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
            //tr.addView(tv);
            //Add team name to top of page. maybe add extra layout above scroll view

            TableRow tr1 = new TableRow(this);
            tv.setText(pos.getKey());
            tv.setTag(pos.getKey());
            tv.setTypeface(null, Typeface.BOLD);
            tv.setTextSize(24);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
            tr1.addView(tv);
            tableLayout.addView(tr1);
            for (Map.Entry<String,String> stat : stats.entrySet()) {
                System.out.println(stats);
                tr = new TableRow(this);
                final TextView tv1 = new TextView(this);
                final TextView tv2 = new TextView(this);
                tv1.setText(stat.getKey());
                tv2.setText(stat.getValue());
                tv1.setTextSize(20);
                tv2.setTextSize(20);
                tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                //tv1.setBackgroundColor(Color.parseColor("#E9E9E9"));
                //tv2.setBackgroundColor(Color.parseColor("#E9E9E9"));
                tr.addView(tv1);
                tr.addView(tv2);
                tableLayout.addView(tr);
            }
        }
    }
}
