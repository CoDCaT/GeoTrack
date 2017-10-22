package com.codcat.geotrack.views.main_screen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codcat.geotrack.R;
import com.codcat.geotrack.model.MyTrack;
import com.codcat.geotrack.presenter.IPresenter;
import com.codcat.geotrack.service.DBHelper;
import com.codcat.geotrack.views.IFragment;

import java.util.List;

public class TrackFragment extends ListFragment implements IFragment {

    List<MyTrack> data;
    DBHelper dbHelper;
    IPresenter presenter;
    MyAdapter adapter;
    private static final int TAP_TO_REFRESH = 1;
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private LayoutInflater mInflater;
    private RelativeLayout mRefreshView;
    private TextView mRefreshViewText;
    private ImageView mRefreshViewImage;
    private ProgressBar mRefreshViewProgress;
    private TextView mRefreshViewLastUpdated;
    private int mRefreshState;
    private int mRefreshViewHeight;
    private int mRefreshOriginalTopPadding;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            dbHelper = new DBHelper(getActivity());
//            presenter  = new GeneralActivityPresenter(dbHelper);
            adapter = new MyAdapter(getActivity(), R.layout.item_list, getDataList());
            setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_track, null);
    }

    public List<MyTrack> getDataList(){
        return presenter.getTracks();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
        //fragTrans.replace(R.id.frameLayout, );
        presenter.onItemClick(position);
        Toast.makeText(getActivity(), "Нажат пункт списка", Toast.LENGTH_SHORT).show();
    }


}
