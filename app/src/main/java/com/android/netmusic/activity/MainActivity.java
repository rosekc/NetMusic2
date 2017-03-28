package com.android.netmusic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.adapter.MainFragmentAdapter;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.utils.MediaUtils;

import static com.android.netmusic.R.string.music;


/**
 * 为了解决抽屉被PlayBar覆盖的问题，将Play单独放在MainActivity
 */
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
    //列表
    private ImageView play_bar_list;
    //整个play_bar
    private LinearLayout play_bar;


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
        //初始化PlayBar组件
        initComponent(getWindow().getDecorView());
        //获取抽屉上部用户信息的View
//        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.nav_header_main,null);
//        initDrawSelect(viewGroup);
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

        initDrawSelect(drawer);
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
     * 初始化抽屉的用户按钮选项
     * @param view
     */
    public void initDrawSelect(View view){
        ImageView user = (ImageView)view.findViewById(R.id.user_image);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 绑定服务，并更新一些状态
     * by焦梦磊
     */
    @Override
    protected void onResume() {
        super.onResume();
        bindPlayService();
        //设置暂停播放的状态
        setPlay_bar_play_pause();
    }

    /**
     * 绑定服务
     * by焦梦磊
     */
    @Override
    protected void onPause() {
        super.onPause();
        unbindPlayService();
    }

    /**
     * 回调方法，更新进度
     * by焦梦磊
     * @param progress
     */
    @Override
    public void publish(int progress) {

    }
    /**
     * 回调方法，更新PlayBar状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void change(int position) {
        if (position >= 0 && position<playService.getMaxCount()&&playService!=null) {
            mp3Info = playService.getCurrentMp3(position);
            //以下是更新主界面下部的UI
            final Bitmap bitmap = MediaUtils.getArtwork(this, mp3Info.getMediaId(), mp3Info.getMediaAblumId(), true, false);
            music_general_ablum.setImageBitmap(bitmap);
            music_general_title.setText(mp3Info.getMediaName());
            music_general_artist.setText(mp3Info.getMediaArtist());
        }
    }
    /**
     * 回调方法，更新PlayBar的状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void changeForState(int position) {
        if(playService.isPlaying()&&playService!=null){
            play_bar_play_pause.setImageResource(R.mipmap.ic_pause);
        }else{
            play_bar_play_pause.setImageResource(R.mipmap.ic_play);
        }
    }

    /**
     * 点击事件的重载方法
     * by焦梦磊
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击主页音乐Bar的播放按钮时的处理逻辑
            case R.id.play_bar_play_pause:
                if(!playService.isPlaying()&&!playService.isPasue()){//音乐没有播放且没有暂停时
                    playService.play(playService.getCurrentPosition());
                }else if(!playService.isPlaying()&&playService.isPasue()){//音乐没有播放且暂停时
                    playService.start();
                }else{//音乐播放时
                    playService.pause();
                }
                break;
            case R.id.play_bar_list://打开播放列表
                Toast.makeText(this,"列表",Toast.LENGTH_SHORT).show();
                break;
            case R.id.play_bar://启动音乐盒
                Intent intent = new Intent(this,PlayBoxActivity.class);
                startActivity(intent);
                //音乐盒启动动画
                overridePendingTransition(R.anim.box_open,android.R.anim.fade_out);
                break;
        }
    }

    /**
     * 初始化PlayBar组件
     * by焦梦磊
     */
    private void initComponent(View view){
        //下面播放bar的暂停和播放按钮
        play_bar_play_pause = (ImageView)view.findViewById(R.id.play_bar_play_pause);
        play_bar_play_pause.setOnClickListener(this);
        //设置暂停播放的状态
        setPlay_bar_play_pause();
        //专辑图片
        music_general_ablum = (ImageView)view.findViewById(R.id.music_general_ablum);
        //歌名
        music_general_title = (TextView)view.findViewById(R.id.music_general_title);
        //歌手
        music_general_artist = (TextView)view.findViewById(R.id.music_general_artist);
        //列表
        play_bar_list = (ImageView)view.findViewById(R.id.play_bar_list);
        play_bar_list.setOnClickListener(this);
        //整个PlayBar
        play_bar = (LinearLayout)view.findViewById(R.id.play_bar);
        play_bar.setOnClickListener(this);
    }

    /**
     * 设置PlayBar暂停或播放的状态
     * by焦梦磊
     */
    private void setPlay_bar_play_pause(){
        if(playService!=null&&playService.isPlaying()&&play_bar_play_pause!=null){
            play_bar_play_pause.setImageResource(R.mipmap.ic_pause);
        }else{
            play_bar_play_pause.setImageResource(R.mipmap.ic_play);
        }
    }
}
