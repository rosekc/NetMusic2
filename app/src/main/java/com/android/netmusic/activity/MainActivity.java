package com.android.netmusic.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.android.netmusic.R;
import com.android.netmusic.adapter.MainFragmentAdapter;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private MainFragmentAdapter mMainFragmentAdapter;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    public String tes = "nihao";
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
    }

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
        mTabLayout.getTabAt(0).setText(R.string.music);
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    //以下是各组员添加代码,添加代码注明功能,自己的姓名                                              //
    //如果需要用到Layout中的资源，但是资源还没有命名id,请各位以下列格式命名，你的姓名_资源名称_资源功能 //
    //例：jiaomenglei_textview_username,姓名:jiaomenglei,资源名称:textview,功能:显示用户名username //
    //获取主Activity中的数据,直接调用mActivity                                                    //
    //PS，如非必须，请不要修改其他代码,如果非得修改，请注释原因                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////
}
