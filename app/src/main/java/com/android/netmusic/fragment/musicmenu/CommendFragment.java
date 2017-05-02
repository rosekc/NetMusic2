package com.android.netmusic.fragment.musicmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.netmusic.R;
import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.adapter.MusicMenuCommendViewPagerAdapter;


/**
 * 个性推荐
 * Created by Android on 2017/3/18.
 */

public class CommendFragment extends Fragment {

    private MainActivity mMainActivity;
    private static CommendFragment instance;
    /**
     * 单例
     * @param mainActivity
     * @return
     */
    public static CommendFragment getInstance(MainActivity mainActivity){
        if(instance==null){
            instance = new CommendFragment();
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
        View view = inflater.inflate(R.layout.fragment_musicmenu_commend,null);
        initView(view);
        return view;
    }

    //幻灯片ViewPager
    private Context context;
    private ViewPager viewPager;
    private void initView(View view){
        viewPager = (ViewPager)view.findViewById(R.id.fragment_musicmenu_commend_viewpager);
        MusicMenuCommendViewPagerAdapter musicMenuCommendViewPagerAdapter = new MusicMenuCommendViewPagerAdapter(mMainActivity.getSupportFragmentManager());
        viewPager.setAdapter(musicMenuCommendViewPagerAdapter);
    }

}