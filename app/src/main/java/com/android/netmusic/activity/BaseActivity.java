package com.android.netmusic.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.netmusic.MusicApp;
import com.android.netmusic.service.PlayService;

/**
 * Actvity基类，用于做应用程序级控制
 * Created by jiaoml on 2017/3/22.
 */

public abstract class BaseActivity extends AppCompatActivity{
    //获取MusicApp实例
    private MusicApp app;
    //音乐播放服务
    public static PlayService playService;
    //判断是否服务绑定过
    public static boolean isBound = false;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MusicApp) getApplication();
        //Toast.makeText(this,view.getId()+"", Toast.LENGTH_SHORT).show();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) iBinder;
            playService = playBinder.getPlayService();
            playService.setMusicUpdateListener(musicUpdateListener);
            musicUpdateListener.onChange(playService.getCurrentPosition());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playService = null;
            isBound = false;
        }
    };

    private PlayService.MusicUpdateListener musicUpdateListener = new PlayService.MusicUpdateListener() {
        @Override
        public void onPublish(int progress) {
            publish(progress);
        }

        @Override
        public void onChange(int position) {
            change(position);
        }

        @Override
        public void onChangeForState(int position) {
            changeForState(position);
        }

    };

    public abstract void publish(int progress);

    public abstract void change(int position);

    public abstract void changeForState(int position);

    //绑定播放服务
    public void bindPlayService() {
        if (!isBound) {
            Intent intent = new Intent(this, PlayService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    public void unbindPlayService() {
        if (isBound) {
            unbindService(conn);
            isBound = false;
        }
    }

    /**
     * 动态保存听歌时间
     */
    private void savePlayTime() {
        SharedPreferences.Editor editor = app.sp.edit();
        long sumTime = app.sp.getLong("sumTime", 0);
        sumTime += 500;
        editor.putLong("sumTime", sumTime);
        editor.commit();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

}
