package com.android.netmusic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.android.netmusic.R;

/**
 * 音乐盒
 * Created by jiaoml on 2017/3/25.
 */

public class PlayBoxActivity extends BaseActivity {

    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_box);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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

    }
    /**
     * 回调方法，更新PlayBar的状态
     * by焦梦磊
     * @param position
     */
    @Override
    public void changeForState(int position) {

    }
}
