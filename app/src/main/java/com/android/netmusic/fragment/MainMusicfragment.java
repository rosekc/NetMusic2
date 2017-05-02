package com.android.netmusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.netmusic.R;
import com.android.netmusic.activity.LikeActivity;
import com.android.netmusic.activity.LocalMusicActivity;
import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.activity.RecentPlayActivity;


/**
 * 本地音乐
 * Created by Android on 2017/3/18.
 */

public class MainMusicfragment  extends Fragment implements View.OnClickListener{

    private MainActivity mMainActivity;
    private static MainMusicfragment instance;

    /**
     * 单例
     * @param mainActivity
     * @return
     */
    public static MainMusicfragment getInstance(MainActivity mainActivity){
        if(instance==null){
            instance = new MainMusicfragment();
        }
        instance.setMainActivity(mainActivity);
        return instance;
    }

    /**
     * 得到主Activity的实例
     * @param mainActivity
     */
    public void setMainActivity(MainActivity mainActivity){
        this.mMainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_music,null);
        //一些初始化操作
        init(view);
        return view;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //以下是各组员添加代码,添加代码注明功能,自己的姓名                                              //
    //如果需要用到Layout中的资源，但是资源还没有命名id,请各位以下列格式命名，你的姓名_资源名称_资源功能 //
    //例：jiaomenglei_textview_username,姓名:jiaomenglei,资源名称:textview,功能:显示用户名username //
    //获取主Activity中的数据,直接调用mActivity                                                    //
    //PS，如非必须，请不要修改其他代码,如果非得修改，请注释原因                                      //
    //////////////////////////////////////////////////////////////////////////////////////////////
    private LinearLayout local_music;
    private LinearLayout recent_music;
    private LinearLayout like_music;
    private void init(View view){
        local_music = (LinearLayout) view.findViewById(R.id.music_local_music);
        local_music.setOnClickListener(this);

        recent_music = (LinearLayout)view.findViewById(R.id.recent_music);
        recent_music.setOnClickListener(this);

        like_music = (LinearLayout)view.findViewById(R.id.like_music);
        like_music.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_local_music:
                Intent intent = new Intent(mMainActivity,LocalMusicActivity.class);
                startActivity(intent);
                mMainActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.recent_music:
                Intent intent1 = new Intent(mMainActivity,RecentPlayActivity.class);
                startActivity(intent1);
                mMainActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.like_music:
                Intent intent2 = new Intent(mMainActivity, LikeActivity.class);
                startActivity(intent2);
                mMainActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}
