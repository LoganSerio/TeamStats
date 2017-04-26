package com.example.logan.test2.com.android.teamstats.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logan.test2.com.android.teamstats.R;
import com.example.logan.test2.com.android.teamstats.Base.Team;

import java.util.List;

/**
 * A class that provides an adapter for showing the teams in the ListView.
 */
public class ListTeamsAdapter extends BaseAdapter {

    public static final String TAG = "ListTeamsAdapter";
    private List<Team> items;
    private LayoutInflater inflater;

    /**
     * The adapter that allows for teams to be displayed.
     * @param context The specific instance.
     * @param listTeams A list of teams.
     */
    public ListTeamsAdapter(Context context, List<Team> listTeams) {
        this.setItems(listTeams);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    /**
     * Gets the size of teams.
     * @return the number of teams.
     */
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    /**
     * Gets a specific instance of a team.
     * @return A team item.
     */
    public Team getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    /**
     * Gets the Id of the team's place in the database.
     * @return the ID of the team in the database.
     */
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    /**
     * A method that gets a View for the desired position.
     * @param index The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return the View for the desired position.
     */
    public View getView(int index, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = inflater.inflate(R.layout.list_item_team, parent, false);
            holder = new ViewHolder();
            holder.txtTeamName = (TextView) v.findViewById(R.id.txt_team_name);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        Team currentItem = getItem(index);
        if(currentItem != null) {
            holder.txtTeamName.setText(currentItem.getName());

        }
        return v;
    }

    /**
     * Gets the items for the team.
     * @return A list of items.
     */
    public List<Team> getItems() {
        return items;
    }

    /**
     * Sets the items equals to the Team list.
     * @param mItems the iteam mTeam is getting set equal to.
     */
    public void setItems(List<Team> mItems) {
        this.items = mItems;
    }

    /**
     * Keeps track of the views in the activity so the program doesn't have to look them up multiple times
     */
    class ViewHolder {
        TextView txtTeamName;
    }

}
