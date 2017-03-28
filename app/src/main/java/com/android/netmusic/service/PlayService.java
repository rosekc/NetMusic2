package com.android.netmusic.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.android.netmusic.MusicApp;
import com.android.netmusic.constant.Constant;
import com.android.netmusic.musicmodel.Mp3Info;
import com.android.netmusic.utils.MediaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 音乐播放的服务组件
 * 实现的功能
 * 1，播放
 * 2，暂停
 * 3，下一首
 * 4，上一首
 * 5，获取当前歌曲的进度
 */
public class PlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private int currentPosition;//表示当前播放第几首歌
    private ArrayList<Mp3Info> mp3Infos = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private MusicUpdateListener musicUpdateListener;
    private Boolean isPause = false;//是否暂停
    private ExecutorService es = Executors.newSingleThreadExecutor();//线程池
    private  MusicApp app;//全局应用程序

    /**
     * 以下是播放模式
     */
    public static final int ORDER_PLAY = 1;//顺序播放
    public static final int RANDOM_PLAY = 2;//随机播放
    public static final int SING_PLAY = 3;//单曲循环
    private int play_mode = ORDER_PLAY;//默认为顺序播放
    /**
     * 以下是播放列表
     */

    /**
     * 随机数
     */
    private Random random = new Random();

    public PlayService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service has create");
        //获得MusicApp的实例
        app = (MusicApp) getApplication();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mp3Infos = MediaUtils.getMp3Infos(this);

        //读取上次推出保存的状态
        currentPosition = app.sp.getInt(Constant.currentposition, 0);
        play_mode = app.sp.getInt(Constant.play_mode, PlayService.ORDER_PLAY);
        es.execute(updateStatusRunnable);
        //注册广播
//        MyReceiver myReceiver = new MyReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("prev");
//        intentFilter.addAction("next");
//        intentFilter.addAction("play_state");
//        registerReceiver(myReceiver, intentFilter);
    }

    /**
     * 退出时保存状态
     */
    private void saveState(){
        SharedPreferences.Editor editor = app.sp.edit();
        editor.putInt(Constant.currentposition,currentPosition);
        editor.putInt(Constant.play_mode,play_mode);
        editor.commit();
    }
    /**
     * 关掉服务
     */
    @Override
    public void onDestroy() {
        System.out.println("测试,看看你被kill了没");
        super.onDestroy();
        if (es != null && !es.isShutdown()) {
            es.shutdown();
            es = null;
        }
        mediaPlayer = null;
        mp3Infos = null;
        musicUpdateListener = null;
        System.out.println("Service has destroy");
    }

    /**
     * 当歌曲播放完毕后的状态
     *
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        switch (play_mode) {
            case ORDER_PLAY:
                next();
                break;
            case RANDOM_PLAY:
                play(random.nextInt(mp3Infos.size() - 1));
                break;
            case SING_PLAY:
                play(currentPosition);
                break;
        }
    }

    /**
     * 发生错误的状态
     *
     * @param mediaPlayer
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }

    /**
     * 更新进度条的线程
     */
    Runnable updateStatusRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (musicUpdateListener != null && mediaPlayer != null && mediaPlayer.isPlaying()) {
                    musicUpdateListener.onPublish(getCurrentProgress());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 获得服务的实例
     */
    public class PlayBinder extends Binder {
        public PlayService getPlayService() {
            return PlayService.this;
        }
    }

    /**
     * 绑定服务
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new PlayBinder();
    }


    /**
     * 播放功能
     *
     * @param position
     */
    public void play(int position) {
        if (position < 0 && position >= mp3Infos.size()) {
            position = 0;
        }
        if(mp3Infos.size()>0){
            Mp3Info mp3Info = mp3Infos.get(position);
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(mp3Info.getmFilePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                currentPosition = position;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (musicUpdateListener != null) {
                musicUpdateListener.onChange(currentPosition);
                musicUpdateListener.onChangeForState(currentPosition);
            }
            //保存状态
            saveState();
            //showNotification();
        }
    }

    /**
     * 开始播放
     */
    public void start() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPause = false;
            //showNotification();
            if (musicUpdateListener != null) {
                musicUpdateListener.onChangeForState(currentPosition);
            }
        }
    }

    /**
     * 暂停功能
     */
    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
            //showNotification();
            if (musicUpdateListener != null) {
                musicUpdateListener.onChangeForState(currentPosition);
            }
        }
    }

    /**
     * 下一首
     */
    public void next() {
        if (play_mode == ORDER_PLAY || play_mode == SING_PLAY) {
            if (currentPosition + 1 > mp3Infos.size() - 1) {
                currentPosition = 0;
            } else {
                currentPosition++;
            }
        }
        if (play_mode == RANDOM_PLAY) {
            currentPosition = random.nextInt(mp3Infos.size() - 1);
        }
        play(currentPosition);
        //showNotification();
    }

    /**
     * 上一首
     */
    public void prev() {
        if (play_mode == ORDER_PLAY || play_mode == SING_PLAY) {
            if (currentPosition - 1 < 0) {
                currentPosition = mp3Infos.size() - 1;
            } else {
                currentPosition--;
            }
        }
        if (play_mode == RANDOM_PLAY) {
            currentPosition = random.nextInt(mp3Infos.size() - 1);
        }
        play(currentPosition);
        //showNotification();
    }

    /**
     * 进度跳转
     *
     * @param msec
     */
    public void seekTo(int msec) {
        mediaPlayer.seekTo(msec);
    }

    /**
     * 判断是否在播放
     *
     * @return
     */
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 通知所有含有播放栏的Activity更新组件状态
     */
    public void notifyall222(){
        if(musicUpdateListener!=null){
            musicUpdateListener.onChange(currentPosition);
            musicUpdateListener.onChangeForState(currentPosition);
        }
    }

    /**
     * 更新状态的接口
     */
    public interface MusicUpdateListener {
        public void onPublish(int progress);

        public void onChange(int position);

        public void onChangeForState(int position);
    }

    /**
     * 设置回调接口
     *
     * @param musicUpdateListener
     */
    public void setMusicUpdateListener(MusicUpdateListener musicUpdateListener) {
        this.musicUpdateListener = musicUpdateListener;
    }


    /**
     * 得到当前播放进度
     *
     * @return
     */
    public int getCurrentProgress() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 取得播放列表
     *
     * @return
     */
    public ArrayList<Mp3Info> getMp3Infos() {
        return mp3Infos;
    }

    /**
     * 设置播放列表
     *
     * @param mp3Infos
     */
    public void setMp3Infos(ArrayList<Mp3Info> mp3Infos) {
        this.mp3Infos = mp3Infos;
    }

    /**
     * 取得当前播放的歌曲位置
     *
     * @return
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * 设置当前播放歌曲的位置
     *
     * @param currentPosition
     */
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * 取得是否暂停
     *
     * @return
     */
    public Boolean getPause() {
        return isPause;
    }

    /**
     * 设置是否暂停
     *
     * @param pause
     */
    public void setPause(Boolean pause) {
        isPause = pause;
    }

    /**
     * 得到播放模式
     *
     * @return
     */
    public int getPlay_mode() {
        return play_mode;
    }

    /**
     * 设置播放模式
     *
     * @param play_mode
     */
    public void setPlay_mode(int play_mode) {
        this.play_mode = play_mode;
    }

    /**
     * 得到播放列表最大歌曲数目
     * @return
     */
    public int getMaxCount(){
        return mp3Infos.size();
    }

    /**
     * 得到当前正在播放的歌曲实例
     * @param currentPosition
     * @return
     */
    public Mp3Info getCurrentMp3(int currentPosition){
        return mp3Infos.get(currentPosition);
    }

    /**
     * 判断当前是否暂停
     * @return
     */
    public boolean isPasue(){
        return isPause;
    }

    /**
     * 判断播放列表是否为空
     *
     * @return
     */
    public boolean isHaveMp3Infos() {
        if (mp3Infos == null) {
            return false;
        }
        return true;
    }

//    /**
//     * 自定义广播，监控通知栏点击事件
//     */
//    public class MyReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            switch (action) {
//                case "next":
//                    next();
//                    break;
//                case "prev":
//                    prev();
//                    break;
//                case "play_state":
//                    if (isPlaying()) {
//                        pause();
//                    } else {
//                        start();
//                    }
//                    break;
//            }
//        }
//    }

//    /**
//     * 设置前台服务通知
//     */
//    public void showNotification() {
//        if(mp3Infos.size()>0){
//            Mp3Info mp3Info = mp3Infos.get(currentPosition);
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setContentText(mp3Info.getMediaName());
//            builder.setContentTitle(mp3Info.getMediaArtist());
//            Bitmap bitmap = MediaUtils.getArtwork(this, mp3Info.getMediaId(), mp3Info.getMediaAblumId(), true, false);
//            builder.setSmallIcon(R.mipmap.ic_notification);
//            builder.setWhen(0);
//            builder.setTicker(mp3Info.getMediaName());
//            //添加自定义View
//            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
//            remoteViews.setImageViewBitmap(R.id.notification_ablum, bitmap);
//            remoteViews.setTextViewText(R.id.notification_title, mp3Info.getMediaName());
//            remoteViews.setTextViewText(R.id.notification_artist, mp3Info.getMediaArtist());
//            builder.setOngoing(true);//设置为常驻通知
//            builder.setAutoCancel(false);//不自动清除
//            builder.setContent(remoteViews);
//            if (isPlaying()) {
//                remoteViews.setImageViewResource(R.id.notification_play_state, R.mipmap.btn_playback_pause);
//            } else {
//                remoteViews.setImageViewResource(R.id.notification_play_state, R.mipmap.btn_playback_play);
//            }
//
//
//            //设置通知栏点击事件
//            PendingIntent next = PendingIntent.getBroadcast(this, 0, new Intent("next"), 0);
//            remoteViews.setOnClickPendingIntent(R.id.notification_next, next);
//            PendingIntent prev = PendingIntent.getBroadcast(this, 0, new Intent("prev"), 0);
//            remoteViews.setOnClickPendingIntent(R.id.notification_play_prev, prev);
//            PendingIntent play_state = PendingIntent.getBroadcast(this, 0, new Intent("play_state"), 0);
//            remoteViews.setOnClickPendingIntent(R.id.notification_play_state, play_state);
//
//            builder.setAutoCancel(false);
//            Intent intent = new Intent(this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//            builder.setContentIntent(pendingIntent);
//            Notification notification = builder.build();
//            startForeground(1, notification);
//        }
//    }
}
