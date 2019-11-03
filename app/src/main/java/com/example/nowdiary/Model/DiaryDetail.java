package com.example.nowdiary.Model;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class DiaryDetail implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private MyTime time;
    private int dateCount;
    private int color;
    private String content;

    public DiaryDetail() {
    }

    public DiaryDetail(String id, MyTime time, int dateCount, int color, String content) {
        this.id = id;
        this.time = time;
        this.dateCount = dateCount;
        this.color = color;
        this.content = content;
    }

    public String getTime() {
        return time.getHour() + " " + time.getMinute();
    }

    public void setTime(MyTime time) {
        this.time = time;
    }

    public int getDateCount() {
        return dateCount;
    }

    public void setDateCount(int dateCount) {
        this.dateCount = dateCount;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static int countDate(Date d1) {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String[] date = formatter.format(today).split(" ");
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR,Integer.parseInt(date[0].split("-")[2]));
        c1.set(Calendar.MONTH,Integer.parseInt(date[0].split("-")[1]));
        c1.set(Calendar.DATE,Integer.parseInt(date[0].split("-")[0]));
        Date d2 = c1.getTime();
        return ((int)d1.getTime() - (int)d2.getTime())/(1000*3600*24);
    }
}
