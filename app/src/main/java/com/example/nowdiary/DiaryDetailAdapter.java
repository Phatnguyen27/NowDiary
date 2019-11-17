package com.example.nowdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nowdiary.Model.DiaryDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiaryDetailAdapter extends RecyclerView.Adapter<DiaryDetailAdapter.ViewHolder> {
    private ArrayList<DiaryDetail> list;
    private Activity activity;
    String time = "";
    String shift = "";
    public DiaryDetailAdapter(ArrayList<DiaryDetail> list,Activity activity) {
        this.list = list;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.diary_detail_component, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DiaryDetail myListData = list.get(position);
        holder.background.getBackground().clearColorFilter();
        int color = myListData.getColor();
        getTimeToShow(myListData.getDateCreate());
        holder.background.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        holder.tv_time.setText(time);
        holder.tv_miniContent.setText(myListData.getContent());
        holder.tv_dateCount.setText(DiaryDetail.countDate(myListData.getDateCreate()));
        holder.tv_shift.setText(shift);
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(view.getContext(), WordActivity.class);
////                intent.putExtra("word", myListData);
////                view.getContext().startActivity(intent);
////            }
////        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void getTimeToShow(Date date) {
        time = "";
        shift = "";
        Calendar itemCal = Calendar.getInstance();
        itemCal.setTime(date);
        time += itemCal.get(Calendar.HOUR) + ":" + itemCal.get(Calendar.MINUTE);
        if (itemCal.get(Calendar.AM_PM) == Calendar.AM) {
            shift += "\nA.M";
        } else {
            shift += "\nP.M";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time,tv_shift,tv_dateCount,tv_miniContent;
        public LinearLayout background;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_time = (TextView) itemView.findViewById(R.id.item_date_hour);
            this.tv_shift = (TextView) itemView.findViewById(R.id.item_date_shift);
            this.tv_dateCount = (TextView) itemView.findViewById(R.id.detail_datecount);
            this.tv_miniContent = (TextView) itemView.findViewById(R.id.detail_content);
            this.background =  itemView.findViewById(R.id.item);
        }
    }
}
