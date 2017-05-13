package com.android.netmusic.fragment.musicmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.netmusic.R;
import com.android.netmusic.activity.ListActivity;
import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.adapter.MyAdapter;
import com.android.netmusic.fragment.musicmenu.view.MyGridView;
import com.android.netmusic.musicmodel.ItemBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 歌单
 * Created by Android on 2017/3/18.
 */

public class MenuFragment extends Fragment {

    private MyGridView gview;

    private MainActivity mMainActivity;
    private static MenuFragment instance;
    /**
     * 单例
     * @param mainActivity
     * @return
     */
    public static MenuFragment getInstance(MainActivity mainActivity){
        if(instance==null){
            instance = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_musicmenu_menu,null);
        final List<ItemBean> itemBeanList=new ArrayList<>();
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing2,//图片
                "123455",//播放量
                "djfkdl",
                "就看到了国家队离开国家阿里看到"
        ));
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing3,
                "1255",
                "djfkd",
                "就看到了国家队离开国"
        ));
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing1,
                "1255",
                "djfkd",
                "就看到了国家队离开国"
        ));
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing4,
                "1255",
                "djfkd",
                "就看到了国家队离开国"
        ));
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing3,
                "1255",
                "djfkd",
                "就看到了国家队离开国"
        ));
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing1,
                "1255",
                "djfkd",
                "就看到了国家队离开国"
        ));
        itemBeanList.add(new ItemBean(
                R.mipmap.ic_musicmenusing2,
                "1255",
                "djfkd",
                "就看到了国家队离开国"
        ));
        gview=(MyGridView)view.findViewById(R.id.gview);
        gview.setAdapter(new MyAdapter(getActivity(),itemBeanList));
        View v = (View)view.findViewById(R.id.LinearLayout_home);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ListActivity.class);
                startActivity(intent);
            }
        });
        gview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        final TextView quanbu=(TextView)view.findViewById(R.id.button1);
        final TextView oumei=(TextView)view.findViewById(R.id.button2);
        final TextView dianzi=(TextView)view.findViewById(R.id.button3);
        final TextView shuochang=(TextView)view.findViewById(R.id.button4);
        oumei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quanbu.setText(oumei.getText().toString());
                gview.setAdapter(new MyAdapter(getActivity(),itemBeanList));
            }
        });
        dianzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quanbu.setText(dianzi.getText().toString());
                gview.setAdapter(new MyAdapter(getActivity(),itemBeanList));
            }
        });
        shuochang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quanbu.setText(shuochang.getText().toString());
                gview.setAdapter(new MyAdapter(getActivity(),itemBeanList));
            }
        });
        return view;
    }

}