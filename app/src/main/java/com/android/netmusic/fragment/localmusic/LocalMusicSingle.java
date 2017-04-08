package com.android.netmusic.fragment.localmusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.activity.LocalMusicActivity;
import com.android.netmusic.adapter.LocalMusicSingleListViewAdapter;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.service.PlayService;
import com.android.netmusic.utils.MediaUtils;

import java.util.ArrayList;

/**
 * 本地音乐，单曲
 * Created by jiaoml on 2017/3/25.
 */

public class LocalMusicSingle extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,LocalMusicSingleListViewAdapter.ItemCallBack{

    private LocalMusicActivity localMusicActivity;
    private static LocalMusicSingle instance;

    /**
     * 单例
     * @param localMusicActivity
     * @return
     */
    public static LocalMusicSingle getInstance(LocalMusicActivity localMusicActivity){
        if(instance==null){
            instance = new LocalMusicSingle();
        }
        instance.setMainActivity(localMusicActivity);
        return instance;
    }

    /**
     * 得到主Activity的实例
     * @param localMusicActivity
     */
    public void setMainActivity(LocalMusicActivity localMusicActivity){
        this.localMusicActivity = localMusicActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_single,null);
        loadListView(view);
        return view;
    }


    private ListView mListView;
    private LocalMusicSingleListViewAdapter mLocalMusicSingleListViewAdapter;
    private ArrayList<Mp3Info> mMp3Infos;
    private LinearLayout playall;//播放全部
    private LinearLayout select;//多选
    private TextView music_count;//音乐数目
    //加载ListView
    private void loadListView(View view){
        //ListView头布局
        ViewGroup top = (ViewGroup) LayoutInflater.from(localMusicActivity).inflate(R.layout.local_music_single_item_top,null);
        playall = (LinearLayout)top.findViewById(R.id.local_music_playall);
        select = (LinearLayout)top.findViewById(R.id.local_music_select);
        music_count = (TextView)top.findViewById(R.id.local_music_count);
        //为Top的两个按钮注册点击事件
        playall.setOnClickListener(this);
        select.setOnClickListener(this);
        mMp3Infos = MediaUtils.getMp3Infos(localMusicActivity);
        //设置歌曲数目
        music_count.setText("(共"+mMp3Infos.size()+"首)");

        mListView = (ListView)view.findViewById(R.id.local_music_single_listview);
        mListView.addHeaderView(top);
        mLocalMusicSingleListViewAdapter = new LocalMusicSingleListViewAdapter(localMusicActivity,mMp3Infos,this);
        mListView.setAdapter(mLocalMusicSingleListViewAdapter);

        //为ListView子项注册点击事件
        mListView.setOnItemClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.local_music_playall://播放全部,暂时默认从第一首开始
                if(localMusicActivity.playService.getPlay_list()!= PlayService.ALL_MUSIC){
                    localMusicActivity.playService.setMp3Infos(mMp3Infos);
                    localMusicActivity.playService.setPlay_list(PlayService.ALL_MUSIC);
                }
                localMusicActivity.playService.play(0);
                break;
            case R.id.local_music_select://多选
                Toast.makeText(localMusicActivity,"多选",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Item子项里面的更多的点击事件
     */
    @Override
    public void click() {
        Toast.makeText(localMusicActivity,"更多",Toast.LENGTH_SHORT).show();
    }

    /**
     * List Item点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(localMusicActivity.playService.getPlay_list()!= PlayService.ALL_MUSIC){
            localMusicActivity.playService.setMp3Infos(mMp3Infos);
            localMusicActivity.playService.setPlay_list(PlayService.ALL_MUSIC);
        }
        if(position!=0&&localMusicActivity.playService!=null){
            localMusicActivity.playService.play(position-1);
            //显示喇叭
            //view.findViewById(R.id.local_music_single_horn).setVisibility(View.VISIBLE);
        }
    }
}
