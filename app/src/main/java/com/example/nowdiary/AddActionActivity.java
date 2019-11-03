package com.example.nowdiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nowdiary.Fragment.DatePickerFragment;
import com.example.nowdiary.Model.DiaryDetail;
import com.example.nowdiary.Model.MyTime;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddActionActivity extends AppCompatActivity {
    private int mYear,mMonth,mDay,mHour,mMinute;
    private int mColor = Color.parseColor("#FFFFFF") ;
    private String content;
    private ImageButton datePickerButton,timePickerButton,colorPickerButton,deleteButton,doneButton,cancelButton;
    private EditText mEditContent,mEditTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        setEvent();
    }

    protected void setEvent() {
        datePickerButton = findViewById(R.id.btn_date_picker);
        timePickerButton = findViewById(R.id.btn_time_picker);
        colorPickerButton = findViewById(R.id.btn_color_picker);
        deleteButton = findViewById(R.id.btn_delete_diary);
        mEditTitle = findViewById(R.id.edit_title);
        mEditContent = findViewById(R.id.edit_content);
        doneButton = findViewById(R.id.btn_done);
        cancelButton = findViewById(R.id.btn_back);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaryDetail journal = new DiaryDetail();
                journal.setTime(new MyTime(mHour,mMinute));
                journal.setContent(mEditContent.getText().toString());
                journal.setColor(mColor);
                Calendar c1 = Calendar.getInstance();
                c1.set(Calendar.YEAR,mYear);
                c1.set(Calendar.MONTH,mMonth);
                c1.set(Calendar.DATE,mDay);
                Date d1 = c1.getTime();
                journal.setDateCount(DiaryDetail.countDate(d1));
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("package",journal);
                intent.putExtra("response",bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                mEditTitle.append(" " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker colorPicker = new ColorPicker(AddActionActivity.this);
                ArrayList<String> colorlist = new ArrayList<>();
                colorlist.add("#d9d9d9");
                colorlist.add("#ffcdd2");
                colorlist.add("#f8bbd0");
                colorlist.add("#e1bee7");
                colorlist.add("#bbdefb");
                colorlist.add("#d7ccc8");
                colorlist.add("#ffe0b2");
                colorlist.add("#fff9c4");
                colorlist.add("#c8e6c9");
                colorlist.add("#b2dfdb");
                colorPicker.setColors(colorlist);
                colorPicker.setDefaultColorButton(R.color.colorPrimary);
                colorPicker.setRoundColorButton(true)
                        .setColumns(5)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                mColor = color;
                            }

                            @Override
                            public void onCancel() {

                            }
                        })
                        .show();
            }
        });
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(AddActionActivity.this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        mEditTitle.append((" " + hourOfDay + " " + minute));
                    }
                },mHour,mMinute,false);
                timePicker.show();
            }
        });
    }
}
