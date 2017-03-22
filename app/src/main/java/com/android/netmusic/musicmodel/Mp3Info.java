package com.android.netmusic.musicmodel;

import android.graphics.Bitmap;


/**
 * 音乐MP3模型
 * Created by 91905 on 2016/8/19 0019.
 */

public class Mp3Info{
    /**
     * id
     */
    private long id;
    /**
     * 歌曲id
     */
    private long MediaId;
    /**
     * 专辑ID
     */
    private long MediaAblumId;
    /**
     * play total time
     */
    private int playDuration = 0;
    /**
     * song name
     */
    private String mediaName = "";
    /**
     * album name
     */
    private String mediaAlbum = "";
    /**
     * artist name
     */
    private String mediaArtist = "";
    /**
     * mYear
     */
    private String mediaYear = "";
    /**
     * fileName
     */
    private String mFileName = "";
    /**
     * mFileType
     */
    private String mFileType = "";
    /**
     * mFileSize
     */
    private String mFileSize = "";
    /**
     * bitmap
     */
    private Bitmap mBitmap = null;
    /**
     * mFilePath
     */
    private String mFilePath = "";
    /**
     * 保存歌曲播放的时间
     */
    private long MediaPlayTime;


    /**
     * 得到ID
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 设置ID
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    public long getMediaId() {
        return MediaId;
    }

    public void setMediaId(long mediaId) {
        MediaId = mediaId;
    }

    public long getMediaAblumId() {
        return MediaAblumId;
    }

    public void setMediaAblumId(long mediaAblumId) {
        MediaAblumId = mediaAblumId;
    }

    /**
     * getBitmap
     * @return
     */
    public Bitmap getmBitmap() {
        return mBitmap;
    }

    /**
     * setBitmap
     * @param mBitmap
     */
    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    /**
     * getPlayDuration
     */
    public int getPlayDuration() {
        return playDuration;
    }
    /**
     * setPlayDuration
     * @param playDuration
     */
    public void setPlayDuration(int playDuration) {
        this.playDuration = playDuration;
    }
    /**
     * getMediaName
     */
    public String getMediaName() {
        return mediaName;
    }
    /**
     * setMediaName
     * @param mediaName
     */
    public void setMediaName(String mediaName) {
//        这个导致了乱码问题，所以注释掉
//        try {
//            mediaName = new String(mediaName.getBytes("ISO-8859-1"), "GB2312");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        this.mediaName = mediaName;
    }
    /**
     * getMediaAlbum
     */
    public String getMediaAlbum() {
        return mediaAlbum;
    }
    /**
     * setMediaAlbum
     * @param mediaAlbum
     */
    public void setMediaAlbum(String mediaAlbum) {
//        这个导致了乱码问题，所以注释掉
//        try {
//            mediaAlbum = new String(mediaAlbum.getBytes("ISO-8859-1"), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        this.mediaAlbum = mediaAlbum;
    }
    /**
     * getMediaArtist
     */
    public String getMediaArtist() {
        return mediaArtist;
    }
    /**
     * setMediaArtist
     * @param mediaArtist
     */
    public void setMediaArtist(String mediaArtist) {
//        这个导致了乱码问题，所以注释掉
//        try {
//            mediaArtist = new String(mediaArtist.getBytes("ISO-8859-1"), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        this.mediaArtist = mediaArtist;
    }
    /**
     * getMediaYear
     */
    public String getMediaYear() {
        return mediaYear;
    }
    /**
     * setMediaYear
     * @param mediaYear
     */
    public void setMediaYear(String mediaYear) {
        this.mediaYear = mediaYear;
    }
    /**
     * getmFileName
     */
    public String getmFileName() {
        return mFileName;
    }
    /**
     * setmFileName
     * @param mFileName
     */
    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }
    /**
     * getmFileType
     */
    public String getmFileType() {
        return mFileType;
    }
    /**
     * setmFileType
     * @param mFileType
     */
    public void setmFileType(String mFileType) {
        this.mFileType = mFileType;
    }
    /**
     * getmFileSize
     */
    public String getmFileSize() {
        return mFileSize;
    }
    /**
     * setmFileSize
     * @param mFileSize
     */
    public void setmFileSize(String mFileSize) {
        this.mFileSize = mFileSize;
    }
    /**
     * getmFilePath
     */
    public String getmFilePath() {
        return mFilePath;
    }
    /**
     * setmFilePath
     * @param mFilePath
     */
    public void setmFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }

    public long getMediaPlayTime() {
        return MediaPlayTime;
    }

    public void setMediaPlayTime(long mediaPlayTime) {
        MediaPlayTime = mediaPlayTime;
    }

}
