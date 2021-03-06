package com.example.logan.test2.com.android.teamstats.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.Base.Statistic;
import com.example.logan.test2.com.android.teamstats.Base.Team;
import com.example.logan.test2.com.android.teamstats.Database.PositionDAO;
import com.example.logan.test2.com.android.teamstats.Database.StatisticDAO;

import java.util.ArrayList;

/**
 * Displays the team overview to the user.
 */
public class TeamOverview extends Fragment {
    PositionDAO positionDAO;
    StatisticDAO statisticDAO;
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
        positionDAO = new PositionDAO(getActivity());
        if (positionDAO.getPositionsOfTeam(team.getId()) != null) {
            posList = (ArrayList<Position>) positionDAO.getPositionsOfTeam(team.getId());
            for (Position pos : posList) {
                statisticDAO = new StatisticDAO(getActivity());
                statList = (ArrayList<Statistic>) statisticDAO.getStatisticsOfPosition(pos.getId());
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
                    tv1.setText(stat.getStatisticName() + ":");
                    tv2.setText(stat.getStatisticValue());
                    tv1.setTextSize(25);
                    tv2.setTextSize(25);
                    tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                    tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
                    tv2.setGravity(Gravity.CENTER);
                    tr.addView(tv1);
                    tr.addView(tv2);
                    tableLayout.addView(tr);
                }
            }
        }
        return rootView;
    }
}
