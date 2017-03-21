package com.android.netmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.fragment.MainCommunityfragment;
import com.android.netmusic.fragment.MainMusicMenufragment;
import com.android.netmusic.fragment.MainMusicfragment;


/**
 * Created by Android on 2017/3/18.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    private final int ITEM_SUM = 3;
    private FragmentManager fm;

    private  MainActivity mMainActivity;
    public MainFragmentAdapter(FragmentManager fm, MainActivity mainActivity){
        super(fm);
        this.fm = fm;
        this.mMainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MainMusicfragment mmf = MainMusicfragment.getInstance(mMainActivity);
                return mmf;
            case 1:
                MainMusicMenufragment mmmf =MainMusicMenufragment.getInstance(mMainActivity);
                return mmmf;
            case 2:
                MainCommunityfragment mcf = MainCommunityfragment.getInstance(mMainActivity);
                return mcf;
        }

        return null;
    }

    @Override
    public int getCount() {
        return ITEM_SUM;
    }
}
