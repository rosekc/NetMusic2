package com.android.netmusic;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.netmusic.constant.Constant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.lidroid.xutils.DbUtils;


/**
 * Created by 91905 on 2016/8/20 0020.
 */

public class MusicApp extends Application {
    //应用程序上下文
    public static Context context;
    //内容分享者
    public static SharedPreferences sp;
    //听歌记录的数据库
    public static DbUtils dbUtilsRecord;
    //喜欢的歌曲的数据库
    public static DbUtils dbUtilsLike;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        dbUtilsRecord = DbUtils.create(getApplicationContext(), Constant.RECORD_DB);
        dbUtilsLike = DbUtils.create(getApplicationContext(),Constant.LIKE_DB);
        context = getApplicationContext();
        //环信相关
        EMOptions options = new EMOptions();
        EMClient.getInstance().init(context,options);
    }
}
