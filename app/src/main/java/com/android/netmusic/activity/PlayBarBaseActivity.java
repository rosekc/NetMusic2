package com.android.netmusic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.netmusic.R;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.utils.MediaUtils;

/**
 * Created by jiaoml on 2017/3/24.
 */

public class PlayBarBaseActivity extends BaseActivity implements View.OnClickListener{


    //下面播放bar的暂停和播放按钮
    private ImageView play_bar_play_pause;
    //Mp3模型实例
    private Mp3Info mp3Info;
    //专辑图片
    private ImageView music_general_ablum;
    //歌名
    private TextView music_general_title;
    //歌手
    private TextView music_general_artist;
    //列表
    private ImageView play_bar_list;
    //整个play_bar
    private LinearLayout play_bar;

    /**
     * 初始化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 将PalyBar放在下面
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        addPlayBar();
    }

    /**
     * 绑定服务，并更新一些状态
     * by焦梦磊
     */
    @Override
    protected void onResume() {
        super.onResume();
        bindPlayService();
        //设置暂停播放的状态
        setPlay_bar_play_pause();
    }

    /**
     * 绑定服务
     * by焦梦磊
     */
    @Override
    protected void onPause() {
        super.onPause();
        unbindPlayService();
    }

    /**
     * 回调方法，更新进度
     * by焦梦磊
     * @param progress
     */
    @Override
    public void publish(int progress) {

    }
    /**
     * 回调方法，更新PlayBar状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void change(int position) {
        if (playService!=null&&position >= 0 && position<playService.getMaxCount()) {
            mp3Info = playService.getCurrentMp3(position);
            //以下是更新主界面下部的UI
            Bitmap bitmap = MediaUtils.getArtwork(this, mp3Info.getMediaId(), mp3Info.getMediaAblumId(), true, false);
            music_general_ablum.setImageBitmap(bitmap);
            music_general_title.setText(mp3Info.getMediaName());
            music_general_artist.setText(mp3Info.getMediaArtist());
        }
    }
    /**
     * 回调方法，更新PlayBar的状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void changeForState(int position) {
        if(playService.isPlaying()&&playService!=null){
            play_bar_play_pause.setImageResource(R.mipmap.ic_pause);
        }else{
            play_bar_play_pause.setImageResource(R.mipmap.ic_play);
        }
    }

    /**
     * 点击事件的重载方法
     * by焦梦磊
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击主页音乐Bar的播放按钮时的处理逻辑
            case R.id.play_bar_play_pause:
                if(!playService.isPlaying()&&!playService.isPasue()){//音乐没有播放且没有暂停时
                    System.out.println("当前播放:"+playService.getCurrentPosition());
                    playService.play(playService.getCurrentPosition());
                }else if(!playService.isPlaying()&&playService.isPasue()){//音乐没有播放且暂停时
                    playService.start();
                }else{//音乐播放时
                    playService.pause();
                }
                break;
            case R.id.play_bar_list://打开播放列表
                //Toast.makeText(this,"列表",Toast.LENGTH_SHORT).show();
                onCreatePopWindow(getWindow().getDecorView());
                break;
            case R.id.play_bar://启动音乐盒
                Intent intent = new Intent(this,PlayBoxActivity.class);
                startActivity(intent);
                //音乐盒启动动画
                overridePendingTransition(R.anim.box_open,android.R.anim.fade_out);
                break;
        }
    }
    /**
     * 添加PlayBar
     * by焦梦磊
     */
    public void addPlayBar(){
        View view = (View) LayoutInflater.from(this).inflate(R.layout.play_bar,null);
        initComponent(view);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        ViewGroup viewGroup = (ViewGroup)getWindow().getDecorView();
        viewGroup.addView(view,params);
    }

    /**
     * 初始化PlayBar组件
     * by焦梦磊
     */
    private void initComponent(View view){
        //下面播放bar的暂停和播放按钮
        play_bar_play_pause = (ImageView)view.findViewById(R.id.play_bar_play_pause);
        play_bar_play_pause.setOnClickListener(this);
        //设置暂停播放的状态
        setPlay_bar_play_pause();
        //专辑图片
        music_general_ablum = (ImageView)view.findViewById(R.id.music_general_ablum);
        //歌名
        music_general_title = (TextView)view.findViewById(R.id.music_general_title);
        //歌手
        music_general_artist = (TextView)view.findViewById(R.id.music_general_artist);
        //列表
        play_bar_list = (ImageView)view.findViewById(R.id.play_bar_list);
        play_bar_list.setOnClickListener(this);
        //整个PlayBar
        play_bar = (LinearLayout)view.findViewById(R.id.play_bar);
        play_bar.setOnClickListener(this);
    }

    /**
     * 设置PlayBar暂停或播放的状态
     * by焦梦磊
     */
    private void setPlay_bar_play_pause(){
        if(playService!=null&&playService.isPlaying()&&play_bar_play_pause!=null){
            play_bar_play_pause.setImageResource(R.mipmap.ic_pause);
        }else{
            play_bar_play_pause.setImageResource(R.mipmap.ic_play);
        }
    }
}
