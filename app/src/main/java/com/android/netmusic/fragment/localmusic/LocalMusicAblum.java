package com.android.netmusic.fragment.localmusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.netmusic.R;
import com.android.netmusic.activity.LocalMusicActivity;

/**
 * 本地音乐，专辑
 * Created by jiaoml on 2017/3/25.
 */

public class LocalMusicAblum extends Fragment{

    private LocalMusicActivity localMusicActivity;
    private static  LocalMusicAblum instance;

    /**
     * 单例
     * @param localMusicActivity
     * @return
     */
    public static LocalMusicAblum getInstance(LocalMusicActivity localMusicActivity){
        if(instance==null){
            instance = new LocalMusicAblum();
        }
        instance.setMainActivity(localMusicActivity);
        return instance;
    }

    /**
     * 得到主Activity的实例
     * @param localMusicActivity
     */
    public void setMainActivity(LocalMusicActivity localMusicActivity){
        this.localMusicActivity = localMusicActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_ablum,null);
        return view;
    }
}
