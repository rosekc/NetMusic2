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
 * 本地音乐，艺术家
 * Created by jiaoml on 2017/3/25.
 */

public class LocalMusicArtist extends Fragment{

    private LocalMusicActivity localMusicActivity;

    /**
     * 单例
     * @param localMusicActivity
     * @return
     */
    public static LocalMusicArtist getInstance(LocalMusicActivity localMusicActivity){
        LocalMusicArtist instance = new LocalMusicArtist();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_artist,null);
        return view;
    }
}
