package com.example.nowdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nowdiary.Model.DiaryDetail;
import com.example.nowdiary.Model.MyTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listView;
    private FloatingActionButton addButton;

    private DatabaseReference dbReference;
    private String userId;
    private ArrayList<DiaryDetail> diaries;
    private DiaryDetailAdapter adapter;

    private ImageButton signoutButton;

    private int REQUEST_CODE_ADD = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diaries = new ArrayList<>();
        diaries.add(new DiaryDetail("0",new MyTime(10,10),10, Color.parseColor("#FFFFFF"),"GG"));

        Intent intent = getIntent();
        if(intent != null) {
            userId = intent.getStringExtra("userId");
        }
        Log.d("USER ID",userId);
        checkForUserIdInDatabase();
        setEvent();
        adapter = new DiaryDetailAdapter(diaries,this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        getData();
    }

    public void setEvent() {
        signoutButton = findViewById(R.id.btn_signout);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginActivity.mGoogleSignInClient.signOut()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    finish();
                                }
                            }
                        });

            }
        });
        listView = findViewById(R.id.listview);
        addButton = findViewById(R.id.add_action_fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActionActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD);
            }
        });
    }

    private void checkForUserIdInDatabase() {
        dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) {
//                            dbReference.child(userId).setValue(1)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful()) {
//
//                                            }
//                                        }
//                                    });
                            dbReference.child(userId).child("Diary").setValue(diaries);
                            dbReference = FirebaseDatabase.getInstance().getReference().child(userId);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("response");
            final DiaryDetail item = (DiaryDetail) bundle.getSerializable("package");
            Toast.makeText(this, "Kha la OK!", Toast.LENGTH_SHORT).show();
            item.setId(dbReference.child("Diary").push().getKey());
            dbReference.child("Diary").setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    diaries.add(item);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void getData() {
        diaries.clear();
        FirebaseDatabase.getInstance().getReference().child(userId).child("Diary").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    DiaryDetail tD = d.getValue(DiaryDetail.class);
                    Log.d("1234567",tD.getContent());
                    diaries.add(tD);
                }
                //sortJournal(diaries);
                dbReference.child("Diary").removeEventListener(this);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void sortJournal(ArrayList<DiaryDetail> journalItems) {
        Collections.sort(journalItems, new Comparator<DiaryDetail>() {
            @Override
            public int compare(DiaryDetail o1, DiaryDetail o2) {
                if (o1.getDateCount() == o2.getDateCount()) {
                    return 0;
                } else if (o1.getDateCount() < o2.getDateCount()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}
