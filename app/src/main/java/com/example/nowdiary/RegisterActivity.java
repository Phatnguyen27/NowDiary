package com.example.nowdiary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity  extends AppCompatActivity {
    private Button mOKButton,mCancelButton;
    private EditText mUsername,mPassword,mValidation;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ProgressDialog myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mOKButton = findViewById(R.id.OK_button);
        mCancelButton = findViewById(R.id.Cancel_Button);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mValidation = findViewById(R.id.password_validation);
    }

    public boolean checkLength(String s) {
        return (s.length() >=8 )? true : false;
    }

    public void setListener() {
        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProgress = new ProgressDialog(RegisterActivity.this);
                myProgress.setTitle("Authenticatiing");
                myProgress.setMessage("Please wait...");
                myProgress.setCancelable(true);
                myProgress.show();
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String validation = mValidation.getText().toString();
                if(checkLength(username) && checkLength(password) && checkLength(validation)) {
                    if(password.equals(validation)) {
                        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                myProgress.dismiss();
                                if(task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this,"Account succesfully created",Toast.LENGTH_LONG).show();
                                    finish();

                                }
                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this,"Validation must match password",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"Username or Password does not match requirement length ( >= 8)",Toast.LENGTH_LONG).show();

                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
