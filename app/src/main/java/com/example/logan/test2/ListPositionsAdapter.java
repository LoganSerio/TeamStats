package com.example.logan.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class ListPositionsAdapter extends ArrayAdapter<Position> {

    Context context;
    int layoutResourceId;
    List<Position> data = null;
    public ListPositionsAdapter(Context context, int resource, List<Position> position) {
        super(context, resource, position);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = position;
    }
    /*
    static class DataHolder {
        TextView positionName;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_position, null);
        }

        Position p = getItem(position);

        if (p != null) {
            TextView posName = (TextView) v.findViewById(R.id.txt_position_name);

            if (posName != null) {
                posName.setText(p.getPositionName());
            }
        }

        return v;
    }
    public void setItems(List<Position> mItems) {
        this.data = mItems;
    }

}

