package com.android.netmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.netmusic.activity.FriendsActivity;
import com.android.netmusic.fragment.friends.ConcernFragment;
import com.android.netmusic.fragment.friends.FansFragment;

/**
 * Created by Android on 2017/3/26.
 */

public class FriendsFramentAdapter extends FragmentPagerAdapter {
    private  FragmentManager fm;
    private FriendsActivity mFriendsActivity;
    private final int ITEM_SUM = 2;
    public FriendsFramentAdapter(FriendsActivity mFriendsActivity, FragmentManager fm) {
        super(fm);
        this.mFriendsActivity = mFriendsActivity;
        this.fm = fm;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ConcernFragment cf = new ConcernFragment();
                return cf;
            case 1:
                FansFragment ff = new FansFragment();
                return ff;

        }
        return null;
    }

    @Override
    public int getCount() {
        return ITEM_SUM;
    }
}
