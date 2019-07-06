package com.rainbow.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rainbow.R;
import com.rainbow.helps.RealmHelper;
import com.rainbow.models.MusicModel;
import com.rainbow.views.PlayMusicView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {
    public static final String MUSIC_ID = "musicId";
    private ImageView mIvBg;
    private TextView mTvName, mTvAuthor;
    private PlayMusicView mPlayMusicView;
    private String mMusicId;
    private RealmHelper mRealmHelper;
    private MusicModel mMusicModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initData();
        initView();
    }

    private void initData() {
        mMusicId = getIntent().getStringExtra(MUSIC_ID);
        mRealmHelper = new RealmHelper();
        mMusicModel = mRealmHelper.getMusic(mMusicId);
    }

    private void initView() {
        mIvBg=fd(R.id.iv_bg);
        mTvName = fd(R.id.tv_name);
        mTvAuthor = fd(R.id.tv_author);
        Glide.with(this)
                .load(mMusicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,10)))
                .into(mIvBg);
        mTvName.setText(mMusicModel.getName());
        mTvAuthor.setText(mMusicModel.getAuthor());
        mPlayMusicView = fd(R.id.play_music_view);
        mPlayMusicView.setMusic(mMusicModel);
        mPlayMusicView.playMusic();
    }
    public void onBackClick(View v){
        onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayMusicView.destroy();
        mRealmHelper.close();
    }

}
