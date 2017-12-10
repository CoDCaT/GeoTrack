package com.codcat.geotrack.views.tracks_screen;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codcat.geotrack.R;
import com.codcat.geotrack.data.MyTrack;

import java.util.List;

public class AdapterTrackList extends ArrayAdapter<MyTrack> {

    private Context context;
    private List<MyTrack> date;

    public AdapterTrackList(Context context, int resource, List<MyTrack> data) {
        super(context, resource, data);
        this.context = context;
        this.date = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item2_list, parent, false);
        TextView txtDateTrack = (TextView) row.findViewById(R.id.txtDateTrack);
        txtDateTrack.setText(context.getString(R.string.txt_date) + date.get(position).getDate());
        TextView txtDistance = (TextView) row.findViewById(R.id.txtDistance);
        txtDistance.setText(context.getString(R.string.txt_distance) + date.get(position).getDistance() + " m");

        return row;
    }
}
