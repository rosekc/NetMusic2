package com.android.netmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.netmusic.activity.LocalMusicActivity;
import com.android.netmusic.fragment.localmusic.LocalMusicAblum;
import com.android.netmusic.fragment.localmusic.LocalMusicArtist;
import com.android.netmusic.fragment.localmusic.LocalMusicFile;
import com.android.netmusic.fragment.localmusic.LocalMusicSingle;


/**
 * Created by Android on 2017/3/18.
 */

public class LocalMusicFragmentAdapter extends FragmentPagerAdapter {
    private final int ITEM_SUM = 4;
    private FragmentManager fm;

    private LocalMusicActivity localMusicActivity;
    public LocalMusicFragmentAdapter(FragmentManager fm, LocalMusicActivity localMusicActivity){
        super(fm);
        this.fm = fm;
        this.localMusicActivity = localMusicActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LocalMusicSingle localMusicSingle = LocalMusicSingle.getInstance(localMusicActivity);
                return localMusicSingle;
            case 1:
                LocalMusicArtist localMusicArtist = LocalMusicArtist.getInstance(localMusicActivity);
                return localMusicArtist;
            case 2:
                LocalMusicAblum localMusicAblum = LocalMusicAblum.getInstance(localMusicActivity);
                return localMusicAblum;
            case 3:
                LocalMusicFile localMusicFile = LocalMusicFile.getInstance(localMusicActivity);
                return localMusicFile;
        }

        return null;
    }

    @Override
    public int getCount() {
        return ITEM_SUM;
    }
}
