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
    private int color;
    private String content;
    private Date dateCreate;

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public DiaryDetail() {
    }

    public DiaryDetail(String id, Date dateCreate, int color, String content) {
        this.id = id;
        this.dateCreate = dateCreate;
        this.color = color;
        this.content = content;
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

    public static String countDate(Date date) {
        String[] month = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        Calendar currentCal = Calendar.getInstance();
        String datediff = "";
        Calendar itemCal = Calendar.getInstance();
        itemCal.set(Calendar.HOUR_OF_DAY, 1);
        itemCal.set(Calendar.MINUTE, 1);
        itemCal.set(Calendar.SECOND, 1);
        Date currentDate = itemCal.getTime();
        itemCal.setTime(date);
        itemCal.set(Calendar.HOUR_OF_DAY, 1);
        itemCal.set(Calendar.MINUTE, 1);
        itemCal.set(Calendar.SECOND, 1);
        Date thatdate = itemCal.getTime();
        long timediff = currentDate.getTime() - thatdate.getTime();
        float daydiff = Math.round(((float) timediff / (1000 * 60 * 60 * 24)) * 10) / 10;
        if (daydiff == 0.0) {
            datediff += "Today";
        } else if (daydiff > 0 && daydiff < 7) {
            datediff += (int) Math.ceil(daydiff) + " days ago";
        } else {
            datediff += month[itemCal.get(Calendar.MONTH)] + " " + itemCal.get(Calendar.DATE);
            if (!(currentCal.get(Calendar.YEAR) == itemCal.get(Calendar.YEAR))) {
                datediff += " " + itemCal.get(Calendar.YEAR);
            }
        }
        return datediff;
    }
}
