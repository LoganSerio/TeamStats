package com.example.logan.test2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Displays the team overview to the user.
 */
public class TeamOverview extends Fragment {
    PositionDAO mPositionDAO;
    StatisticDAO mStatisticDAO;
    ArrayList<Position> posList;
    ArrayList<Statistic> statList;

    @Override
    /**
     * Generates a fragment for the user interface.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container	If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The view instance of the fragment.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_overview, container, false);

        Team team = (Team) getActivity().getIntent().getSerializableExtra("Team");
        String name = team.getName();
        TextView tvTN = (TextView) rootView.findViewById(R.id.tvTeamName);
        tvTN.setText(name);
        tvTN.setTextSize(45);
        tvTN.setTypeface(null,Typeface.BOLD);

        TableLayout tableLayout;
        tableLayout = (TableLayout) rootView.findViewById(R.id.tb);
        TableRow tr;
        mPositionDAO = new PositionDAO(getActivity());
        posList = (ArrayList<Position>) mPositionDAO.getPositionsOfTeam(team.getId());
        for (Position pos : posList) {
            mStatisticDAO = new StatisticDAO(getActivity());
            statList = (ArrayList<Statistic>) mStatisticDAO.getStatisticsOfPosition(pos.getId());
            final TextView tv = new TextView(getActivity());
            TableRow tr1 = new TableRow(getActivity());
            tv.setText(pos.getPositionName());
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
            tv.setTextSize(34);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
            tr1.addView(tv);
            tableLayout.addView(tr1);
            for (Statistic stat : statList) {
                tr = new TableRow(getActivity());
                final TextView tv1 = new TextView(getActivity());
                final TextView tv2 = new TextView(getActivity());
                tv1.setText(stat.getStatisticName()+":");
                tv2.setText(stat.getStatisticValue());
                tv1.setTextSize(25);
                tv2.setTextSize(25);
                tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setGravity(Gravity.END);
                tr.addView(tv1);
                tr.addView(tv2);
                tableLayout.addView(tr);
            }
        }
        /*
        //Sets textview to hold team name on top of team overview
        Team team = (Team) getActivity().getIntent().getSerializableExtra("Team");
        String name = team.getName();
        TextView tvTN = (TextView) rootView.findViewById(R.id.tvTeamName);
        tvTN.setText(name);
        tvTN.setTextSize(45);
        tvTN.setTypeface(null,Typeface.BOLD);

        //Fills in and makes textviews based on number of positions and stats
        TableLayout tableLayout;
        tableLayout = (TableLayout) rootView.findViewById(R.id.tb);
        TableRow tr;
        for (Map.Entry<String,TreeMap<String,String>> pos : map.entrySet()) {
            TreeMap<String,String> stats = new TreeMap<String, String> ((Map<String,String>) (pos.getValue()));
            tr = new TableRow(getActivity());
            final TextView tv = new TextView(getActivity());
            TableRow tr1 = new TableRow(getActivity());
            tv.setText(pos.getKey());
            tv.setTag(pos.getKey());
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
            tv.setTextSize(34);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
            tr1.addView(tv);
            tableLayout.addView(tr1);
            for (Map.Entry<String,String> stat : stats.entrySet()) {
                System.out.println(stats);
                tr = new TableRow(getActivity());
                final TextView tv1 = new TextView(getActivity());
                final TextView tv2 = new TextView(getActivity());
                tv1.setText(stat.getKey());
                tv2.setText(stat.getValue());
                tv1.setTextSize(30);
                tv2.setTextSize(30);
                tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tv2.setGravity(Gravity.END);
                tr.addView(tv1);
                tr.addView(tv2);
                tableLayout.addView(tr);
            }
        }
        */
        return rootView;
    }
}
