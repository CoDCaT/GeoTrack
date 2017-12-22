package com.codcat.geotrack.views.tracks_screen;


import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codcat.geotrack.App;
import com.codcat.geotrack.R;
import com.codcat.geotrack.data.MyTrack;
import com.codcat.geotrack.utils.ItemTouchHelperAdapter;
import com.codcat.geotrack.utils.ItemTouchHelperViewHolder;
import com.codcat.geotrack.utils.OnMyListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTrackList extends RecyclerView.Adapter<AdapterTrackList.ViewHolder> implements ItemTouchHelperAdapter {



    private Context context;
    private List<MyTrack> date;
    private final OnMyListener mListener;
    private boolean undoOn;


    public AdapterTrackList(Context context, List<MyTrack> data, OnMyListener mListener) {
        this.context = context;
        this.date = data;
        this.mListener = mListener;
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //return super.getView(position, convertView, parent);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = inflater.inflate(R.layout.item2_list, parent, false);
//        TextView txtDateTrack = (TextView) row.findViewById(R.id.txtDateTrack);
//        txtDateTrack.setText(context.getString(R.string.txt_date) + date.get(position).getDate());
//        TextView txtDistance = (TextView) row.findViewById(R.id.txtDistance);
//        txtDistance.setText(context.getString(R.string.txt_distance) + date.get(position).getDistance() + " m");
//
//        return row;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(App.appContext).inflate(R.layout.item2_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MyTrack track = date.get(position);

        holder.txtDateTrack.setText("Дата: " + track.getDate());
        holder.txtDistance.setText("Дистанция: " + track.getDistance() + " m");

        holder.itemView.setOnClickListener(v -> mListener.onItemClicked(position, holder.itemView));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        @BindView(R.id.txtDateTrack) TextView txtDateTrack;
        @BindView(R.id.txtDistance) TextView txtDistance;
        @BindView(R.id.viewForeground) ConstraintLayout viewForeground;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(date, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


}
