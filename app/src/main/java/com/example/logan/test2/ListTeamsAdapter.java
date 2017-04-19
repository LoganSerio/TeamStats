package com.example.logan.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListTeamsAdapter extends BaseAdapter {

    public static final String TAG = "ListTeamsAdapter";

    private List<Team> mItems;
    private LayoutInflater mInflater;

    public ListTeamsAdapter(Context context, List<Team> listTeams) {
        this.setItems(listTeams);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Team getItem(int position) {
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
            v = mInflater.inflate(R.layout.list_item_team, parent, false);
            holder = new ViewHolder();
            holder.txtTeamName = (TextView) v.findViewById(R.id.txt_team_name);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Team currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtTeamName.setText(currentItem.getName());

        }

        return v;
    }

    public List<Team> getItems() {
        return mItems;
    }

    public void setItems(List<Team> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtTeamName;
    }

}
