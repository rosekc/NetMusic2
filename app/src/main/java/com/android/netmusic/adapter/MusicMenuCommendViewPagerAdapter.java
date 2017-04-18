package com.android.netmusic.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.android.netmusic.fragment.musicmenu.viewpager.CommendViewPagerFragment1;
import com.android.netmusic.fragment.musicmenu.viewpager.CommendViewPagerFragment2;
import com.android.netmusic.fragment.musicmenu.viewpager.CommendViewPagerFragment3;
import com.android.netmusic.fragment.musicmenu.viewpager.CommendViewPagerFragment4;
import com.android.netmusic.fragment.musicmenu.viewpager.CommendViewPagerFragment5;
import com.android.netmusic.fragment.musicmenu.viewpager.CommendViewPagerFragment6;

/**
 * Created by jiaoml on 17-4-18.
 */

public class MusicMenuCommendViewPagerAdapter extends FragmentPagerAdapter {
    public MusicMenuCommendViewPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CommendViewPagerFragment1();
            case 1:
                return new CommendViewPagerFragment2();
            case 2:
                return new CommendViewPagerFragment3();
            case 3:
                return new CommendViewPagerFragment4();
            case 4:
                return new CommendViewPagerFragment5();
            case 5:
                return new CommendViewPagerFragment6();
        }
        return null;
    }
}
