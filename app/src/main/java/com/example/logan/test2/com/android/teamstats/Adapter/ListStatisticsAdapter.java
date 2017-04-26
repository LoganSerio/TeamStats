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

/**
 * A class supplies an adapter for adding statistic objects to a listview
 */
public class ListStatisticsAdapter extends ArrayAdapter<Statistic> {

    Context context;
    int layoutResourceId;
    List<Statistic> data = null;

    /**
     * The adapter that allows for teams to be displayed.
     * @param context The specific instance.
     * @param resource Information about the layout xml
     * @param statistic A list of statistics
     */
    public ListStatisticsAdapter(Context context, int resource, List<Statistic> statistic) {
        super(context, resource, statistic);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = statistic;
    }

    /**
     * A method that gets a View for the desired index.
     * @param index The index of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return the View for the desired position.
     */
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

    /**
     * Puts the items from the list into the listview
     * @param mItems the item being entered into the listview
     */
    public void setItems(List<Statistic> mItems) {
        this.data = mItems;
    }
}
