package com.android.netmusic.musicmodel;

/**
 * 播放记录模型
 * Created by 91905 on 2016/8/24 0024.
 */

public class Record {
    private int id;
    private int year;
    private int month;
    private int day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
