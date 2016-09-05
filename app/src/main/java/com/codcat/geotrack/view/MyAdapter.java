package com.codcat.geotrack.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codcat.geotrack.R;
import com.codcat.geotrack.model.MyTrack;

import java.util.List;

public class MyAdapter extends ArrayAdapter<MyTrack> {

    private Context context;
    private List<MyTrack> date;

    public MyAdapter(Context context, int resource, List<MyTrack> data) {
        super(context, resource, data);
        this.context = context;
        this.date = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_list, parent, false);
        TextView txtDateTrack = (TextView) row.findViewById(R.id.txtDateTrack);
        txtDateTrack.setText(date.get(position).getDate());
        TextView txtDistance = (TextView) row.findViewById(R.id.txtDistance);
        txtDistance.setText(date.get(position).getDistance() + " m");

        return row;
    }
}
