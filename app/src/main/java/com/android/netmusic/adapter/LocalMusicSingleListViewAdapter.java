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
 * 本地音乐，单曲，adapter
 * Created by jiaoml on 2017/3/25.
 */

public class LocalMusicSingleListViewAdapter extends BaseAdapter implements View.OnClickListener{
    private ArrayList<Mp3Info> mMp3Infos;
    private Context mContext;
    private Boolean isDataFist;//判断是否是第一条数据
    private ItemCallBack mItemCallBack;
    public LocalMusicSingleListViewAdapter(Context context, ArrayList<Mp3Info> mp3Infos, ItemCallBack itemCallBack){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.local_music_single_item,null);
            viewHolder = new ViewHolder();
            viewHolder.musicname = (TextView)convertView.findViewById(R.id.musicname);
            viewHolder.artist_ablum = (TextView)convertView.findViewById(R.id.artist_ablum);
            viewHolder.local_music_more = (LinearLayout)convertView.findViewById(R.id.local_music_more);
            viewHolder.local_music_single_horn = (ImageView)convertView.findViewById(R.id.local_music_single_horn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Mp3Info mp3Info = mMp3Infos.get(position);

        String musicname = mp3Info.getMediaName();//歌名
        String artist = mp3Info.getMediaArtist();//歌手
        String ablum = mp3Info.getMediaAlbum();

        //设置每个条目的样式
        if(position== BaseActivity.playService.getCurrentPosition()){
            viewHolder.musicname.setText(musicname);
            viewHolder.artist_ablum.setText(artist+"-"+ablum);
            viewHolder.musicname.setTextColor(mContext.getResources().getColor(R.color.base_red));
            viewHolder.artist_ablum.setTextColor(mContext.getResources().getColor(R.color.base_red));
            viewHolder.local_music_single_horn.setVisibility(View.VISIBLE);
        }else{
            viewHolder.musicname.setText(musicname);
            viewHolder.artist_ablum.setText(artist+"-"+ablum);
            viewHolder.musicname.setTextColor(mContext.getResources().getColor(R.color.current_play_musicname));
            viewHolder.artist_ablum.setTextColor(mContext.getResources().getColor(R.color.current_play_aritst));
            viewHolder.local_music_single_horn.setVisibility(View.GONE);
        }
        //注册事件
        viewHolder.local_music_more.setOnClickListener(this);
        return convertView;
    }


    class ViewHolder{
        TextView musicname;//歌名
        TextView artist_ablum;//歌手和专辑
        LinearLayout local_music_more;
        ImageView local_music_single_horn;
    }

    public interface ItemCallBack{
        public void click();
    }

    @Override
    public void onClick(View v) {
        mItemCallBack.click();
    }
}
