package com.android.netmusic.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.netmusic.R;
import com.android.netmusic.adapter.FriendsFramentAdapter;

public class FriendsActivity extends PlayBarBaseActivity {
    private ViewPager mViewPager;
    private FriendsFramentAdapter mFriendsFragmentAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        //设置ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.myfriends);
        //初始化
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.friends_viewpager);
        mFriendsFragmentAdapter = new FriendsFramentAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mFriendsFragmentAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.lqm_friends_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("关注");
        mTabLayout.getTabAt(1).setText("粉丝");
    }
}
