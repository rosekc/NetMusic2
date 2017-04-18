package com.android.netmusic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.adapter.LocalMusicSingleListViewAdapter;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.service.PlayService;
import com.android.netmusic.utils.MediaUtils;

import java.util.ArrayList;

/**
 * Created by jiaoml on 2017/4/3.
 */

public class RecentPlayActivity extends PlayBarBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,LocalMusicSingleListViewAdapter.ItemCallBack{


    private ListView mListView;
    private LocalMusicSingleListViewAdapter mLocalMusicSingleListViewAdapter;
    private ArrayList<Mp3Info> mMp3Infos;
    private LinearLayout playall;//播放全部
    private LinearLayout select;//多选
    private TextView music_count;//音乐数目

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_local_music_single);
        //设置ActionBar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.recent_play);
        //加载布局
        initView(getWindow().getDecorView());
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
     * 初始化布局
     * @param view
     */
    private void initView(View view){
        //ListView头布局
        ViewGroup top = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.local_music_single_item_top,null);
        playall = (LinearLayout)top.findViewById(R.id.local_music_playall);
        select = (LinearLayout)top.findViewById(R.id.local_music_select);
        music_count = (TextView)top.findViewById(R.id.local_music_count);
        //为Top的两个按钮注册点击事件
        playall.setOnClickListener(this);
        select.setOnClickListener(this);
        mMp3Infos = MediaUtils.getRecentMusic(this);

        //设置歌曲数目
        music_count.setText("(共"+mMp3Infos.size()+"首)");

        mListView = (ListView)view.findViewById(R.id.local_music_single_listview);
        mListView.addHeaderView(top);

        mLocalMusicSingleListViewAdapter = new LocalMusicSingleListViewAdapter(this,mMp3Infos,this);
        mListView.setAdapter(mLocalMusicSingleListViewAdapter);

        //为ListView子项注册点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(playService.getPlay_list()!= PlayService.RECENT_MUSIC){
                    playService.setMp3Infos(mMp3Infos);
                    playService.setPlay_list(PlayService.RECENT_MUSIC);
                }
                if(position!=0&&playService!=null){
                    playService.play(position-1);
                }
                mLocalMusicSingleListViewAdapter.notifyDataSetChanged();
            }
        });
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
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.local_music_playall://播放全部,暂时默认从第一首开始
                if(playService.getPlay_list()!= PlayService.RECENT_MUSIC){
                    playService.setMp3Infos(mMp3Infos);
                    playService.setPlay_list(PlayService.RECENT_MUSIC);
                }
                playService.play(0);
                break;
            case R.id.local_music_select://多选
                Toast.makeText(this,"多选",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Item子项里面的更多的点击事件
     */
    @Override
    public void click() {
        Toast.makeText(this,"更多",Toast.LENGTH_SHORT).show();
    }
}
