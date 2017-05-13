package com.android.netmusic.fragment.musicmenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.adapter.FiveAdapter;
import com.android.netmusic.adapter.FourAdapter;
import com.android.netmusic.adapter.OneAdapter;
import com.android.netmusic.adapter.SixAdapter;
import com.android.netmusic.adapter.ThreeAdapter;
import com.android.netmusic.adapter.TwoAdapter;
import com.android.netmusic.fragment.musicmenu.view.ImageBarnnerFramLayout;
import com.android.netmusic.fragment.musicmenu.view.ImageBarnnerViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 个性推荐
 * Created by Android on 2017/3/18.
 */

public class CommendFragment extends Fragment implements ImageBarnnerViewGroup.ImageBannerLister{

    private MainActivity mMainActivity;
    private GridView gridView_01;
    private GridView gridView_02;
    private GridView gridView_03;
    private GridView gridView_04;
    private GridView gridView_05;
    private GridView gridView_06;

    private TextView tv_daily;

    private ImageBarnnerFramLayout mGroup;
    private  int[] lunbotu_ids =new int[]{
            R.mipmap.lunbotu_1,
            R.mipmap.lunbotu_2,
            R.mipmap.lunbotu_3,
            R.mipmap.lunbotu_4,
            R.mipmap.lunbotu_5,
            R.mipmap.lunbotu_6,
            R.mipmap.lunbotu_7,
            R.mipmap.lunbotu_8
    };
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

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicmenu_commend,null);

        //此时我们需要计算出我们当前手机的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        ImageBarnnerFramLayout.WITTH = dm.widthPixels;

        mGroup=(ImageBarnnerFramLayout) view.findViewById(R.id.sunyanni_viewgroup_lunbo);
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i <lunbotu_ids.length ; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),lunbotu_ids[i]);
            list.add(bitmap);
        }
        mGroup.addBitmaps(list);

        //第一个GridView
        gridView_01 = (GridView)view.findViewById(R.id.gridview_01);
        gridView_01.setAdapter(new OneAdapter(getActivity()));

        //第二个GridView
        gridView_02 = (GridView)view.findViewById(R.id.gridview_02);
        gridView_02.setAdapter(new TwoAdapter(getActivity()));

        //第三个GridView
        gridView_03 = (GridView)view.findViewById(R.id.gridview_03);
        gridView_03.setAdapter(new ThreeAdapter(getActivity()));

        //第四个GridView
        gridView_04 = (GridView)view.findViewById(R.id.gridview_04);
        gridView_04.setAdapter(new FourAdapter(getActivity()));

        //第五个GridView
        gridView_05 = (GridView)view.findViewById(R.id.gridview_05);
        gridView_05.setAdapter(new FiveAdapter(getActivity()));

        //第六个GridView
        gridView_06 = (GridView)view.findViewById(R.id.gridview_06);
        gridView_06.setAdapter(new SixAdapter(getActivity()));

        tv_daily = (TextView) view.findViewById(R.id.recommmend_daily);
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("dd");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        tv_daily.setText(str);

        return view;
    }

    @Override
    public void clickImageIndex(int pos) {
        Toast.makeText(this.getActivity(),"pos="+pos,Toast.LENGTH_SHORT).show();
    }

}