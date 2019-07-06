package com.rainbow.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelp {
    private static MediaPlayerHelp instance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;


    public static MediaPlayerHelp getInstance(Context context){
        if(instance==null){
            synchronized (MediaPlayerHelp.class){
                if (instance==null){
                    instance=new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }
    private MediaPlayerHelp(Context context){
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }
    public void setPath(String path){
        mPath=path;
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if(onMediaPlayerHelperListener!=null){
                    onMediaPlayerHelperListener.onPrepared(mediaPlayer);
                }
            }
        });
        //        监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });



    }
    public String getPath(){
        return mPath;
    }
    public void start(){
        if(mMediaPlayer.isPlaying()){
            return;
        }else{
            mMediaPlayer.start();
        }
    }
    public void pause(){
        mMediaPlayer.pause();
    }
    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer m);
        void onCompletion(MediaPlayer m);
    }

}
