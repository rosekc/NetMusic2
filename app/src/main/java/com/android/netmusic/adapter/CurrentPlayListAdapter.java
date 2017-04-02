package com.android.netmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.netmusic.R;
import com.android.netmusic.activity.BaseActivity;
import com.android.netmusic.musicmodel.Mp3Info;

import java.util.ArrayList;

/**
 * Created by jiaoml on 2017/4/2.
 */

public class CurrentPlayListAdapter extends BaseAdapter{

    private ArrayList<Mp3Info> mMp3Infos;
    private Context mContext;
    private Boolean isDataFist;//判断是否是第一条数据
    private ItemCallBack mItemCallBack;
    public CurrentPlayListAdapter(Context context,ArrayList<Mp3Info> mp3Infos,ItemCallBack itemCallBack){
        this.mContext = context;
        this.mMp3Infos = mp3Infos;
        isDataFist = false;
        mItemCallBack = itemCallBack;
    }
    @Override
    public int getCount() {
        return mMp3Infos.size();
    }

    @Override
    public Object getItem(int position) {
        return mMp3Infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.current_play_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.horn = (ImageView)convertView.findViewById(R.id.current_play_list_horn);
            viewHolder.musicname = (TextView)convertView.findViewById(R.id.current_play_list_musicname);
            viewHolder.line = (TextView)convertView.findViewById(R.id.current_play_list_line);
            viewHolder.artist = (TextView)convertView.findViewById(R.id.current_play_list_artist);
            viewHolder.delete = (LinearLayout)convertView.findViewById(R.id.current_play_list_delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Mp3Info mp3Info = mMp3Infos.get(position);
        //设置每个条目的样式
        if(position== BaseActivity.playService.getCurrentPosition()){
            viewHolder.musicname.setText(mp3Info.getMediaName());
            viewHolder.artist.setText(mp3Info.getMediaArtist());
            viewHolder.musicname.setTextColor(mContext.getResources().getColor(R.color.base_red));
            viewHolder.line.setTextColor(mContext.getResources().getColor(R.color.base_red));
            viewHolder.artist.setTextColor(mContext.getResources().getColor(R.color.base_red));
            viewHolder.horn.setVisibility(View.VISIBLE);
        }else{
            viewHolder.musicname.setText(mp3Info.getMediaName());
            viewHolder.artist.setText(mp3Info.getMediaArtist());
            viewHolder.musicname.setTextColor(mContext.getResources().getColor(R.color.current_play_musicname));
            viewHolder.artist.setTextColor(mContext.getResources().getColor(R.color.current_play_aritst));
            viewHolder.line.setTextColor(mContext.getResources().getColor(R.color.current_play_line));
            viewHolder.horn.setVisibility(View.GONE);
        }



        //注册事件
        MyOnClickListener myOnClickListener = new MyOnClickListener(position);
        viewHolder.delete.setOnClickListener(myOnClickListener);
        return convertView;
    }


    class ViewHolder{
        TextView musicname;
        TextView artist;
        TextView line;
        LinearLayout delete;
        ImageView horn;
    }

    public interface ItemCallBack{
        public void click(int position);
    }

    class MyOnClickListener implements View.OnClickListener{

        private int position;
        public MyOnClickListener(int postion){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            mItemCallBack.click(position);
        }
    }
}
