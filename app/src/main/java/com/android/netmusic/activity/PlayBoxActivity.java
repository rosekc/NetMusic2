package com.android.netmusic.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.constant.Constant;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.service.PlayService;
import com.android.netmusic.utils.FastBlur;
import com.android.netmusic.utils.MediaUtils;
import com.android.netmusic.wiget.RoundImageView;

/**
 * 音乐盒
 * Created by jiaoml on 2017/3/25.
 */

public class PlayBoxActivity extends BaseActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    //音乐盒总的布局北京
    private LinearLayout box_bg;
    //返回按钮
    private Button box_back;
    //歌名和歌手
    private TextView  box_musicname,box_musicartist;
    //当前时间，总时间
    private TextView box_currenttime,box_totaltime;
    //进度条
    private SeekBar box_seekbar;
    //播放状态,下一曲，上一曲，播放模式，列表
    private Button box_state,box_next,box_pev,box_mode,box_list;
    //圆形图片
    private RoundImageView round_ablunm;
    //Mp3
    private Mp3Info mp3Info;
    private static final int UPDATE_TIME = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_box);
        //设置全屏并且显示状态栏
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initComponent();
    }

    //初始化组件
    private void initComponent(){
        //音乐盒总的布局北京
        box_bg = (LinearLayout)findViewById(R.id.box_bg);
        //返回按钮
        box_back = (Button)findViewById(R.id.box_back);
        box_back.setOnClickListener(this);
        //歌名和歌手
        box_musicname = (TextView)findViewById(R.id.box_musicname);
        box_musicartist = (TextView)findViewById(R.id.box_musicartist);
        //当前时间,总时间
        box_currenttime = (TextView)findViewById(R.id.box_currenttime);
        box_totaltime = (TextView)findViewById(R.id.box_totaltime);
        //进度条
        box_seekbar = (SeekBar)findViewById(R.id.box_seekbar);
        box_seekbar.setOnSeekBarChangeListener(this);
        //播放状态,下一曲，上一曲，播放模式，列表
        box_state = (Button)findViewById(R.id.box_state);
        box_state.setOnClickListener(this);
        box_next = (Button)findViewById(R.id.box_next);
        box_next.setOnClickListener(this);
        box_pev = (Button)findViewById(R.id.box_pev);
        box_pev.setOnClickListener(this);
        box_mode = (Button)findViewById(R.id.box_mode);
        box_mode.setOnClickListener(this);
        box_list = (Button)findViewById(R.id.box_list);
        box_list.setOnClickListener(this);
        //圆形图片
        round_ablunm = (RoundImageView)findViewById(R.id.round_ablunm);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        //音乐盒关闭动画
        overridePendingTransition(android.R.anim.fade_in, R.anim.box_close);
    }

    /**
     * 绑定服务，并更新一些状态
     * by焦梦磊
     */
    @Override
    protected void onResume() {
        super.onResume();
        bindPlayService();
        //初始化更新一些状态
        updateState();
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


    //更新进度条的Handler
    class MyHandler extends Handler{
        private PlayBoxActivity mPlayBoxActivity;
        public MyHandler(PlayBoxActivity playBoxActivity){
            this.mPlayBoxActivity = playBoxActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mPlayBoxActivity!=null){
                switch (msg.what){
                    case UPDATE_TIME://更新时间
                        mPlayBoxActivity.box_currenttime.setText(MediaUtils.formatTime(msg.arg1));
                }
            }
        }
    }

    private MyHandler mMyHandler  = new MyHandler(this);
    /**
     * 回调方法，更新进度
     * by焦梦磊
     * @param progress
     */
    @Override
    public void publish(int progress) {
        //更新进度条
        box_seekbar.setProgress(progress);
        //更新时间
        Message msg = mMyHandler.obtainMessage(UPDATE_TIME);
        msg.arg1 = progress;
        mMyHandler.sendMessage(msg);
    }
    /**
     * 回调方法，更新音乐盒状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void change(int position) {
        if (playService!=null&&position >= 0 && position<playService.getMaxCount()) {
            mp3Info = playService.getCurrentMp3(position);
            //歌名和歌手
            box_musicname.setText(mp3Info.getMediaName());
            box_musicartist.setText(mp3Info.getMediaArtist());
            //设置背景图
            Bitmap bg_bitmap;
            bg_bitmap = MediaUtils.getArtwork(this, mp3Info.getMediaId(), mp3Info.getMediaAblumId(), true, true);
            bg_bitmap = FastBlur.doBlur(bg_bitmap, Constant.ABLUM_SKIN_BLUR, true);
            box_bg.setBackground(new BitmapDrawable(bg_bitmap));
            //更新seekbar的最大值
            box_seekbar.setMax(mp3Info.getPlayDuration());
            //更新总时间
            box_totaltime.setText(MediaUtils.formatTime(mp3Info.getPlayDuration()));
            //更新圆形图片
            bg_bitmap = MediaUtils.getArtwork(this, mp3Info.getMediaId(), mp3Info.getMediaAblumId(), true, false);
            round_ablunm.setImageBitmap(bg_bitmap);
        }
    }

    /**
     * 回调方法，更新音乐盒状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void changeForState(int position) {
        updateState();
    }

    //刚打开的时候更新状态
    private void updateState(){
        if(playService==null){
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.ablum_rotate);
        //更新播放的状态
        if(playService.isPlaying()){
            box_state.setBackgroundResource(R.mipmap.ic_box_pause);
            round_ablunm.startAnimation(animation);
        }else{
            box_state.setBackgroundResource(R.mipmap.ic_box_play);
            round_ablunm.clearAnimation();
        }
        //更新播放模式的状态
        switch (playService.getPlay_mode()){
            case PlayService.ORDER_PLAY:
                box_mode.setBackgroundResource(R.mipmap.ic_box_mode_order);
                break;
            case PlayService.RANDOM_PLAY:
                box_mode.setBackgroundResource(R.mipmap.ic_box_mode_random);
                break;
            case PlayService.SING_PLAY:
                box_mode.setBackgroundResource(R.mipmap.ic_box_mode_single);
                break;
        }
    }
    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.box_state://播放状态
                if(!playService.isPlaying()&&!playService.isPasue()){//音乐没有播放且没有暂停时
                    playService.play(playService.getCurrentPosition());
                }else if(!playService.isPlaying()&&playService.isPasue()){//音乐没有播放且暂停时
                    playService.start();
                }else{//音乐播放时
                    playService.pause();
                }
                break;
            case R.id.box_pev://上一首
                playService.prev();
                break;
            case R.id.box_next://下一首
                playService.next();
                break;
            case R.id.box_mode://播放模式
                switch (playService.getPlay_mode()){
                    case PlayService.ORDER_PLAY:
                        box_mode.setBackgroundResource(R.mipmap.ic_box_mode_random);
                        playService.setPlay_mode(PlayService.RANDOM_PLAY);
                        Toast.makeText(this,"随机播放",Toast.LENGTH_SHORT).show();
                        break;
                    case PlayService.RANDOM_PLAY:
                        box_mode.setBackgroundResource(R.mipmap.ic_box_mode_single);
                        playService.setPlay_mode(PlayService.SING_PLAY);
                        Toast.makeText(this,"单曲循环",Toast.LENGTH_SHORT).show();
                        break;
                    case PlayService.SING_PLAY:
                        box_mode.setBackgroundResource(R.mipmap.ic_box_mode_order);
                        playService.setPlay_mode(PlayService.ORDER_PLAY);
                        Toast.makeText(this,"列表播放",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.box_back://返回
                finish();
                break;
        }
    }

    /**
     * SeekBar事件
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser&&playService.isHaveMp3Infos()){
            playService.pause();
            playService.seekTo(progress);
            playService.start();
        }
    }

    /**
     * SeekBar事件
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * SeekBar事件
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


}
