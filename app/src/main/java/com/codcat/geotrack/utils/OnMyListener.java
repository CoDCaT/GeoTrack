package com.codcat.geotrack.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnMyListener {
    void onItemClicked(int position, View view);
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

}
