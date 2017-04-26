package com.example.logan.test2.com.android.teamstats.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Statistic;

import java.util.List;

public class ListStatisticsAdapter extends ArrayAdapter<Statistic> {

    Context context;
    int layoutResourceId;
    List<Statistic> data = null;

    public ListStatisticsAdapter(Context context, int resource, List<Statistic> statistic) {
        super(context, resource, statistic);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = statistic;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_statistic, null);
        }
        Statistic p = getItem(index);
        if(p != null) {
            TextView statName = (TextView) v.findViewById(R.id.txt_statistic_name);
            if(statName != null) {
                statName.setText(p.getStatisticName());
            }
        }
        return v;
    }

    public void setItems(List<Statistic> mItems) {
        this.data = mItems;
    }
}
