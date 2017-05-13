package com.android.netmusic.fragment.musicmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.netmusic.R;
import com.android.netmusic.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 排行榜
 * Created by Android on 2017/3/18.
 */

public class RankFragment extends Fragment {

    private MainActivity mMainActivity;
    private static RankFragment instance;
    private ListView listView;
    private int[]images={R.mipmap.glo6,R.mipmap.glo2,R.mipmap.glo3,R.mipmap.glo4,R.mipmap.glo5,
            R.mipmap.glo1,R.mipmap.glo7,R.mipmap.glo8,R.mipmap.glo9,R.mipmap.glo10,R.mipmap.glo11,
            R.mipmap.glo12,R.mipmap.glo13,R.mipmap.glo14,R.mipmap.glo15,R.mipmap.glo16,
            R.mipmap.glo17,R.mipmap.glo1};
    private String[]text={"云音乐ACG音乐榜","UK排行榜周榜","美国Billboard周榜","法国 NRJ Vos Hits 周榜"
            ,"iTunes榜","云音乐电音榜","Beatport全球电子舞曲榜","日本Oricon周榜","云音乐古典音乐榜"
            ,"Hit FM Top榜","KTV唛榜","台湾Hito排行榜","中国TOP排行榜（港台榜）"
            ,"中国TOP排行榜（内地榜）","香港电台中文歌曲龙虎榜","华语金曲榜","中国嘻哈榜","云音乐电音榜"};
    private int[]images2={R.mipmap.use1,R.mipmap.use2};
    private String[]text2={"音乐达人榜","音乐新人榜"};
    /**
     * 单例
     * @param mainActivity
     * @return
     */
    public static RankFragment getInstance(MainActivity mainActivity){
        if(instance==null){
            instance = new RankFragment();
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
        View view = inflater.inflate(R.layout.fragment_musicmenu_rank,null);
        listView = (ListView)view.findViewById(R.id.listview);
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),getData(),R.layout.fragment_musicmenu_rank_listitem,
                new String[]{"xin","one","two","three","img"},
                new int[]{R.id.xin,R.id.one,R.id.two,R.id.three,R.id.img});
        listView.setAdapter(adapter);
        GridView gridView=(GridView)view.findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter(getActivity(),images,text));
        GridView gridView2=(GridView)view.findViewById(R.id.gridView2);
        gridView2.setAdapter(new MyAdapter2(getActivity(),images2,text2));
        return view;
    }
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();

        Map<String,Object>map=new HashMap<String,Object>();
        map.put("img",R.mipmap.guan1);
        map.put("xin","每天更新");
        map.put("one","1.");
        map.put("two","2.");
        map.put("three","3.");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("img",R.mipmap.guan2);
        map.put("xin","每天更新");
        map.put("one","1.");
        map.put("two","2.");
        map.put("three","3.");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("img",R.mipmap.guan3);
        map.put("xin","每周四更新");
        map.put("one","1.");
        map.put("two","2.");
        map.put("three","3.");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("img",R.mipmap.guan4);
        map.put("xin","每周四更新");
        map.put("one","1.");
        map.put("two","2.");
        map.put("three","3.");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("img",R.mipmap.guan5);
        map.put("xin","");
        map.put("one","1.");
        map.put("two","2.");
        map.put("three","3.");
        list.add(map);

        return list;
    }
    private static class MyAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private int[] images;
        private String[] text;
        public MyAdapter(Context context, int[] images, String[] text){
            this.images = images;
            this.text = text;
            layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = layoutInflater.inflate(R.layout.fragment_musicmenu_rank_girditem,null);
            ImageView iv = (ImageView) v.findViewById(R.id.gridViewImg);
            TextView tv = (TextView) v.findViewById(R.id.text);
            iv.setImageResource(images[position]);
            tv.setText(text[position]);
            return v;
        }
    }
    private static class MyAdapter2 extends BaseAdapter{
        private LayoutInflater layoutInflater;
        private int[] images2;
        private String[] text2;
        public MyAdapter2(Context context,int[] images,String[] text){
            this.images2 = images;
            this.text2 = text;
            layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return images2.length;
        }

        @Override
        public Object getItem(int position) {
            return images2[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = layoutInflater.inflate(R.layout.fragment_musicmenu_rank_girditem,null);
            ImageView iv = (ImageView) v.findViewById(R.id.gridViewImg);
            TextView tv = (TextView) v.findViewById(R.id.text);
            iv.setImageResource(images2[position]);
            tv.setText(text2[position]);
            return v;
        }
    }

}