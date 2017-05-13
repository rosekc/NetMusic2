package com.android.netmusic.fragment.musicmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.android.netmusic.R;
import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.adapter.RadioItem;
import com.android.netmusic.adapter.RadioList;
import com.android.netmusic.adapter.RadioListAdapter;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * 主播电台
 * Created by Android on 2017/3/18.
 */

public class RadioFragment extends Fragment {

    private MainActivity mMainActivity;
    private static RadioFragment instance;
    /**
     * 单例
     * @param mainActivity
     * @return
     */
    public static RadioFragment getInstance(MainActivity mainActivity){
        if(instance==null){
         instance = new RadioFragment();
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
    private List<Integer> testBannerImage;
    private List<RadioList> testRadioList;
    private List<RadioItem> testRadioItem;

    private void initTestSource() {
        testBannerImage = new ArrayList<>();
        testRadioList = new ArrayList<>();
        testRadioItem = new ArrayList<>();
        testBannerImage.add(R.mipmap.banner1);
        testBannerImage.add(R.mipmap.banner1);
        testBannerImage.add(R.mipmap.banner2);
        testBannerImage.add(R.mipmap.banner2);
        testRadioItem.add(new RadioItem("他改变了中国", "Mr. Frog", R.mipmap.radio_item1));
        testRadioItem.add(new RadioItem("他改变了中国", "Mr. Frog", R.mipmap.radio_item1));
        testRadioItem.add(new RadioItem("他改变了中国", "Mr. Frog", R.mipmap.radio_item1));
        testRadioList.add(new RadioList("去西方看看1", testRadioItem));
        testRadioList.add(new RadioList("去西方看看2", testRadioItem));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicmenu_radio,null);
        initTestSource();
//        ListView listview = (ListView) view.findViewById(R.id.radio_list_view_rosekc);
//        listview.setAdapter(new RadioListAdapter(getContext(), testRadioList));
        ExpandableListView expandableListView = (ExpandableListView)
                view.findViewById(R.id.radio_list_view_rosekc);
        expandableListView.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.radio_header, null));
        expandableListView.setAdapter(new RadioListAdapter(getContext(), testRadioList));
        for (int i = 0; i < testRadioList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        Banner banner = (Banner) view.findViewById(R.id.radio_banner_rosekc);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        banner.setImages(testBannerImage).start();
        return view;
    }
}