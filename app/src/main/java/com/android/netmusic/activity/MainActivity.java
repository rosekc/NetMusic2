package com.android.netmusic.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.netmusic.R;
import com.android.netmusic.adapter.MainFragmentAdapter;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.utils.MediaUtils;

import static com.android.netmusic.R.string.music;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    //ViewPage
    private ViewPager mViewPager;
    //ViewPage的适配器
    private MainFragmentAdapter mMainFragmentAdapter;
    //ToolBar
    private Toolbar mToolbar;
    //TabLayout
    private TabLayout mTabLayout;
    //下面播放bar的暂停和播放按钮
    private ImageView play_bar_play_pause;
    //Mp3模型实例
    private Mp3Info mp3Info;
    //专辑图片
    private ImageView music_general_ablum;
    //歌名
    private TextView music_general_title;
    //歌手
    private TextView music_general_artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout)findViewById(R.id.main_tab_layout);
        //初始化toolbar
        initToolbar();
        //初始化抽屉
        initDraw();
        //初始化ViewPage
        initViewPage();
        //初始化组件
        initComponent();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //初始化界面UI布局///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 初始化主界面ViewPage
     */
    private void initViewPage(){
        mViewPager = (ViewPager)findViewById(R.id.main_view_page);
        mMainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(mMainFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //将toolbar和viewPage关联
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.getTabAt(0).setText(music);
        mTabLayout.getTabAt(1).setText(R.string.musicmenu);
        mTabLayout.getTabAt(2).setText(R.string.commity);
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar(){
        //初始化toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }
    /**
     * 初始化抽屉
     */
    private void initDraw(){
        //初始化抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 侧滑抽屉关闭的时候
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /**
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sreach, menu);
        return true;
    }

    /**
     * 初始化组件
     */
    private void initComponent(){
        //下面播放bar的暂停和播放按钮
        play_bar_play_pause = (ImageView)findViewById(R.id.play_bar_play_pause);
        play_bar_play_pause.setOnClickListener(this);
        //专辑图片
        music_general_ablum = (ImageView)findViewById(R.id.music_general_ablum);
        //歌名
        music_general_title = (TextView)findViewById(R.id.music_general_title);
        //歌手
        music_general_artist = (TextView)findViewById(R.id.music_general_artist);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////
    //绑定服务，这一部分由焦梦磊处理
    @Override
    protected void onResume() {
        super.onResume();
        bindPlayService();
    }
    //解绑服务
    @Override
    protected void onPause() {
        super.onPause();
        unbindPlayService();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////
    //以下是回调方法,这一部分由焦梦磊来处理///////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //回调方法,更新进度
    @Override
    public void publish(int progress) {

    }
    //回调方法，更新播放的歌曲
    @Override
    public void change(int position) {
        if (position >= 0 && position<playService.getMaxCount()) {
            mp3Info = playService.getCurrentMp3(position);
            //以下是更新主界面下部的UI
            final Bitmap bitmap = MediaUtils.getArtwork(this, mp3Info.getMediaId(), mp3Info.getMediaAblumId(), true, false);
            music_general_ablum.setImageBitmap(bitmap);
            music_general_title.setText(mp3Info.getMediaName());
            music_general_artist.setText(mp3Info.getMediaArtist());
        }
    }
    //回调方法,更新状态
    @Override
    public void changeForState(int position) {
        if(playService.isPlaying()){
            play_bar_play_pause.setImageResource(R.mipmap.ic_pause);
        }else{
            play_bar_play_pause.setImageResource(R.mipmap.ic_play);
        }
    }
    //点击事件的回调方法
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击主页音乐Bar的播放按钮时的处理逻辑
            case R.id.play_bar_play_pause:
                if(!playService.isPlaying()&&!playService.isPasue()){//音乐没有播放且没有暂停时
                    playService.play(0);
                }else if(!playService.isPlaying()&&playService.isPasue()){//音乐没有播放且暂停时
                    playService.start();
                }else{//音乐播放时
                    playService.pause();
                }
                break;
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
