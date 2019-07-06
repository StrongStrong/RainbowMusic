package com.rainbow.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.rainbow.R;
import com.rainbow.activitys.WellcomeActivity;
import com.rainbow.helps.MediaPlayerHelp;
import com.rainbow.models.MusicModel;


public class MusicService extends Service {

    public static final int NOTIFICATION_ID = 1;

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;

    public MusicService() {
    }

    public class MusicBind extends Binder {

        /**
         * 设置音乐
         */
        public void setMusic(MusicModel musicModel) {
            mMusicModel = musicModel;
            //startForeground();
        }

        /**
         * 播放音乐
         */
        public void playMusic() {
            /**
             * 1、判断当前音乐是否是已经在播放的音乐
             * 2、如果当前的音乐是已经在播放的音乐的话，那么就直接执行start方法
             * 3、如果当前播放的音乐不是需要播放的音乐的话，那么就调用setPath的方法
             */
            if (mMediaPlayerHelp.getPath() != null
                    && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())) {
                mMediaPlayerHelp.start();
            } else {
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }

                });
            }
        }

        /**
         * 停止音乐
         */
        public void stopMusic() {
            mMediaPlayerHelp.pause();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);
    }

    /**
     * 设置服务在前台的展示
     */
    private void startForeground() {
        String CHANNEL_ONE_ID = "com.rainbow";
        String CHANNEL_NAME = "rainbow";
        String CHANNEL_DESCRIPTION = "this is default channel!";

        //创建 PendingIntent ，用作notification 被点击时跳转的intent。
        Intent intent = new Intent(getApplicationContext(), WellcomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        NotificationChannel mNotificationChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            mNotificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationChannel.setDescription(CHANNEL_DESCRIPTION);
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }
        //创建 Notification
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, CHANNEL_ONE_ID);
        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        builder.setContentTitle(mMusicModel.getName());
        builder.setContentText(mMusicModel.getAuthor());
        builder.setSmallIcon(R.mipmap.logo);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        //设置 Notification 的前台展示
        startForeground(NOTIFICATION_ID, notification);


    }


}
