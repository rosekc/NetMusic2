package com.android.netmusic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.netmusic.R;
import com.android.netmusic.adapter.LocalMusicFragmentAdapter;

/**
 * 本地音乐
 * Created by jiaoml on 2017/3/23.
 */

public class LocalMusicActivity extends PlayBarBaseActivity{
    private ViewPager mViewPager;
    private LocalMusicFragmentAdapter mLocalMusicFragmentAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        //设置ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.local_music);
        //初始化布局
        initTabLayout(getWindow().getDecorView());
    }

    /**
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sreach, menu);
        menu.add("扫描本地音乐");
        menu.add("选择排序方式");
        menu.add("获取封面歌词");
        menu.add("升级音质");
        return true;
    }

    /**
     * 菜单项目被选中
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://返回事件
                finish();
                break;
        }
        return true;
    }

    /**
     * 设置退出动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 初始化布局
     */
    private void initTabLayout(View view){
        mLocalMusicFragmentAdapter = new LocalMusicFragmentAdapter(getSupportFragmentManager(),this);
        mViewPager = (ViewPager)view.findViewById(R.id.local_music_viewpager);
        mViewPager.setAdapter(mLocalMusicFragmentAdapter);
        mTabLayout = (TabLayout)view.findViewById(R.id.local_music_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(R.string.local_music_single);
        mTabLayout.getTabAt(1).setText(R.string.local_music_artist);
        mTabLayout.getTabAt(2).setText(R.string.local_music_ablum);
        mTabLayout.getTabAt(3).setText(R.string.local_music_file);
    }

}
