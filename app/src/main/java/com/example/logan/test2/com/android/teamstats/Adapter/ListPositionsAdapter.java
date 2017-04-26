package com.example.logan.test2.com.android.teamstats.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.logan.test2.com.android.teamstats.Base.Position;
import com.example.logan.test2.com.android.teamstats.R;

import java.util.List;


/**
 * A class supplies an adapter for adding position objects to a listview
 */
public class ListPositionsAdapter extends ArrayAdapter<Position> {

    Context context;
    int layoutResourceId;
    List<Position> data = null;

    /**
     * Creates a new position adapter
     * @param context Lets newly created objects understand what's been happening in the program
     * @param resource information about the layout xml
     * @param position A the positions to be added to the adapter
     */
    public ListPositionsAdapter(Context context, int resource, List<Position> position) {
        super(context, resource, position);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = position;
    }

    /**
     * A method that gets a View for the desired index.
     * @param index The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return the View for the desired index.
     */
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_position, null);
        }
        Position p = getItem(index);
        if (p != null) {
            TextView posName = (TextView) v.findViewById(R.id.txt_position_name);

            if (posName != null) {
                posName.setText(p.getPositionName());
            }
        }
        return v;
    }

    /**
     * Puts the items from the list into the listview
     * @param mItems the item being entered into the listview
     */
    public void setItems(List<Position> mItems) {
        this.data = mItems;
    }
}

