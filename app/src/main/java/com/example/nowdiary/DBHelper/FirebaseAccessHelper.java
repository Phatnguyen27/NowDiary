package com.example.nowdiary.DBHelper;

import android.content.Context;

import com.example.nowdiary.Model.DiaryDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseAccessHelper {
    public DatabaseReference dbReference;
    private Context context;
    public FirebaseAccessHelper(Context context) {
        this.context = context;
        dbReference = FirebaseDatabase.getInstance().getReference();
    }
//    public ArrayList<DiaryDetail> getDiary(int offset) {
//    }
}
