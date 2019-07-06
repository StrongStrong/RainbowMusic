package com.rainbow.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rainbow.R;
import com.rainbow.helps.MediaPlayerHelp;
import com.rainbow.models.MusicModel;
import com.rainbow.services.MusicService;

public class PlayMusicView extends FrameLayout {
    private Context mContext;
    private MusicModel mMusicModel;
    private MusicService.MusicBind mMusicBinder;
    private Intent mServiceIntent;
    private View mView;
    private ImageView mIvIcon,mIvNeedle,mIvPlay;
    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;
    private FrameLayout mFlPlayMusic;
    private boolean isPlaying,isBindService;

    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    private void init(Context context){
        mContext=context;
        mView=LayoutInflater.from(mContext).inflate(R.layout.play_music,this,false);
        mFlPlayMusic=mView.findViewById(R.id.fl_play_music);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                trigger();
            }
        });
        mIvNeedle=mView.findViewById(R.id.iv_needle);
        mIvPlay=mView.findViewById(R.id.iv_play);
        mIvIcon=mView.findViewById(R.id.iv_icon);
        mPlayMusicAnim=AnimationUtils.loadAnimation(mContext,R.anim.play_music_anim);
        mPlayNeedleAnim=AnimationUtils.loadAnimation(mContext,R.anim.play_needle_anim);
        mStopNeedleAnim=AnimationUtils.loadAnimation(mContext,R.anim.stop_needle_anim);
        addView(mView);
    }
    private void trigger(){
        if(isPlaying){
            stopMusic();
        }else{
            playMusic();
        }

    }
    public void playMusic(){
        isPlaying=true;
        mIvPlay.setVisibility(GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);
        startMusicService();

    }
    public void stopMusic(){
        isPlaying=false;
        mIvPlay.setVisibility(VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);
        mMusicBinder.stopMusic();


    }
    public void setMusicIcon(){
        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }
    /**
     * 设置音乐播放模型
     */
    public void setMusic (MusicModel musicModel) {
        this.mMusicModel = musicModel;

        setMusicIcon();
    }
    /**
     * 启动音乐服务
     */
    private void startMusicService () {

        if (mServiceIntent == null) {
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        } else {
            mMusicBinder.playMusic();
        }


//        当前未绑定，绑定服务，同时修改绑定状态
        if (!isBindService) {
            isBindService = true;
            mContext.bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
        }
    }
    /**
     * 销毁方法，需要在 activity 被销毁的时候调用
     */
    public void destroy () {
//        如果已绑定服务，则解除绑定，同时修改绑定状态
        if (isBindService) {
            isBindService = false;
            mContext.unbindService(conn);
        }

    }
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBinder = (MusicService.MusicBind) service;
            mMusicBinder.setMusic(mMusicModel);
            mMusicBinder.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
