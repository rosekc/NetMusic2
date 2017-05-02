package com.android.netmusic.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.MusicApp;
import com.android.netmusic.R;
import com.android.netmusic.adapter.CurrentPlayListAdapter;
import com.android.netmusic.service.PlayService;

/**
 * Actvity基类，用于做应用程序级控制
 * Created by jiaoml on 2017/3/22.
 */

public abstract class BaseActivity extends AppCompatActivity implements CurrentPlayListAdapter.ItemCallBack,AdapterView.OnItemClickListener{
    //获取MusicApp实例
    private MusicApp app;
    //音乐播放服务
    public static PlayService playService;
    //判断是否服务绑定过
    public static boolean isBound = false;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MusicApp) getApplication();
        //Toast.makeText(this,view.getId()+"", Toast.LENGTH_SHORT).show();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) iBinder;
            playService = playBinder.getPlayService();
            playService.setMusicUpdateListener(musicUpdateListener);
            musicUpdateListener.onChange(playService.getCurrentPosition());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playService = null;
            isBound = false;
        }
    };

    private PlayService.MusicUpdateListener musicUpdateListener = new PlayService.MusicUpdateListener() {
        @Override
        public void onPublish(int progress) {
            publish(progress);
        }

        @Override
        public void onChange(int position) {
            change(position);
        }

        @Override
        public void onChangeForState(int position) {
            changeForState(position);
        }

    };

    public abstract void publish(int progress);

    public abstract void change(int position);

    public abstract void changeForState(int position);

    //绑定播放服务
    public void bindPlayService() {
        if (!isBound) {
            Intent intent = new Intent(this, PlayService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    public void unbindPlayService() {
        if (isBound) {
            unbindService(conn);
            isBound = false;
        }
    }



    private CurrentPlayListAdapter currentPlayListAdapter;//适配器
    private ListView listView;
    /**
     * 创建Pop窗体
     * @param view
     */
    protected void onCreatePopWindow(View view){
        //获取布局
        final View current_play_list = LayoutInflater.from(this).inflate(R.layout.current_play_list,null);
        //创建Popwindow
        PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, getWindow().getDecorView().getHeight()*3/5);

        LinearLayout current_play_list_random = (LinearLayout)current_play_list.findViewById(R.id.current_play_list_random);

        //标题
        final TextView current_play_list_title = (TextView)current_play_list.findViewById(R.id.current_play_list_title);
        //图标
        final ImageView current_play_list_ic = (ImageView) current_play_list.findViewById(R.id.current_play_list_ic);
        //设置播放模式
        switch (playService.getPlay_mode()){
            case PlayService.ORDER_PLAY:
                current_play_list_ic.setImageResource(R.mipmap.ic_box_mode_order);
                current_play_list_title.setText("列表播放");
                break;
            case PlayService.RANDOM_PLAY:
                current_play_list_ic.setImageResource(R.mipmap.ic_box_mode_random);
                current_play_list_title.setText("随机播放");
                break;
            case PlayService.SING_PLAY:
                current_play_list_ic.setImageResource(R.mipmap.ic_box_mode_single);
                current_play_list_title.setText("单曲循环");
                break;
        }
        //随机播放
        current_play_list_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (playService.getPlay_mode()){
                    case PlayService.ORDER_PLAY:
                        playService.setPlay_mode(PlayService.RANDOM_PLAY);
                        current_play_list_ic.setImageResource(R.mipmap.ic_box_mode_random);
                        current_play_list_title.setText("随机播放");
                        break;
                    case PlayService.RANDOM_PLAY:
                        playService.setPlay_mode(PlayService.SING_PLAY);
                        current_play_list_ic.setImageResource(R.mipmap.ic_box_mode_single);
                        current_play_list_title.setText("单曲循环");
                        break;
                    case PlayService.SING_PLAY:
                        playService.setPlay_mode(PlayService.ORDER_PLAY);
                        current_play_list_ic.setImageResource(R.mipmap.ic_box_mode_order);
                        current_play_list_title.setText("列表播放");
                        break;
                }
                changeForState(playService.getCurrentPosition());
            }
        });
        //ListView以及一些控制
        listView = (ListView)current_play_list.findViewById(R.id.current_play_list_list_view);
        currentPlayListAdapter = new CurrentPlayListAdapter(this,playService.getMp3Infos(),this);
        listView.setAdapter(currentPlayListAdapter);
        listView.setSelection(playService.getCurrentPosition());
        listView.setOnItemClickListener(this);

        //设置动画
        popupWindow.setAnimationStyle(R.style.current_play_list_anim);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        //将布局加载到Popwindow里
        popupWindow.setContentView(current_play_list);
        //设置显示位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        //背景相关
        backgroundAlpha(0.7f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 设置Activity透明度
     * @param fl
     */
    protected void backgroundAlpha(float fl){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = fl;
        getWindow().setAttributes(lp);
    }


    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////


    /**
     * ListView点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        playService.play(position);
        currentPlayListAdapter.notifyDataSetChanged();
    }

    /**
     * 从播放列表中删除某首歌
     * @param position
     */
    @Override
    public void click(int position) {
        Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
    }
}
