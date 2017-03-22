package com.android.netmusic;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by Android on 2017/3/21.
 */

public class MyApplication extends Application{
    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        EMOptions options = new EMOptions();
        EMClient.getInstance().init(applicationContext,options);
    }
}
