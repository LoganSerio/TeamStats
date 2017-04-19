package com.example.logan.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Trevor on 4/18/17.
 */

public class ListPositionsAdapter extends BaseAdapter {

    public static final String TAG = "ListPositionsAdapter";

    private List<Position> mItems;
    private LayoutInflater mInflater;

    public ListPositionsAdapter(Context context, List<Position> listTeams) {
        this.setItems(listTeams);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Position getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_position, parent, false);
            holder = new ViewHolder();
            holder.txtPositionName = (TextView) v.findViewById(R.id.txt_position_name);
            holder.txtTeamName = (TextView) v.findViewById(R.id.txt_team_name);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Position currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtPositionName.setText(currentItem.getPositionName());
            holder.txtTeamName.setText(currentItem.getTeam().getName());
        }

        return v;
    }

    public List<Position> getItems() {
        return mItems;
    }

    public void setItems(List<Position> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtPositionName;
        TextView txtTeamName;
    }

}
