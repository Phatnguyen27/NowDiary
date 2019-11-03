package com.example.nowdiary.Model;

import java.io.Serializable;

public class MyTime implements Serializable {
    private int hour;
    private int minute;

    public MyTime() {
    }

    public MyTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
