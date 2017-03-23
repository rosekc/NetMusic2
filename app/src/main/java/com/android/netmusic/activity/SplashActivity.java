package com.android.netmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.android.netmusic.R;
import com.android.netmusic.service.PlayService;

/**
 * 闪屏页
 * Created by jiaoml on 2017/3/22.
 */

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //启动服务
        Intent intentService = new Intent(this, PlayService.class);
        startService(intentService);

        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        //三秒后启动
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 3000);
    }

    //以下方法暂时先不管
    @Override
    public void publish(int progress) {

    }

    @Override
    public void change(int position) {

    }

    @Override
    public void changeForState(int position) {

    }
}
