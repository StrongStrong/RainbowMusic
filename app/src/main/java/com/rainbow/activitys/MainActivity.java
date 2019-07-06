package com.rainbow.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rainbow.R;
import com.rainbow.adapters.MusicGridAdapter;
import com.rainbow.adapters.MusicListAdapter;
import com.rainbow.helps.RealmHelper;
import com.rainbow.models.MusicSourceModel;
import com.rainbow.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {
    private RecyclerView mRvGrid;
    private RecyclerView mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;
    private RealmHelper mRealmHelper;
    private MusicSourceModel mMusicSourceModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new RealmHelper();
        mMusicSourceModel = mRealmHelper.getMusicSource();
    }

    private void initView() {
        initNavBar(false,"雨过天晴",true);
        mRvGrid = fd(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.albumMarginSize),mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter = new MusicGridAdapter(this,mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mGridAdapter);
        mRvList =fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter = new MusicListAdapter(this,mRvList,mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }
}
