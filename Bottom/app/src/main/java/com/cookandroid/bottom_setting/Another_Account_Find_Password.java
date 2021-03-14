package com.cookandroid.bottom_setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Another_Account_Find_Password extends AppCompatActivity {

    Button set_password;
    private FirebaseAuth firebaseAuth ;
    ProgressDialog progressDialog;
    EditText email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        firebaseAuth = FirebaseAuth.getInstance();
        set_password = findViewById(R.id.set_password);
        set_password.setOnClickListener(change_password);
        email=findViewById(R.id.find_email);
        progressDialog = new ProgressDialog(this);
    }


    Button.OnClickListener change_password = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (firebaseAuth.getCurrentUser() != null) {
                if (v==set_password){
                    progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요");
                    progressDialog.show();

                    String emailAddress = email.getText().toString().trim();
                    firebaseAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Another_Account_Find_Password.this,"이메일을 보냈습니다", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                            else{
                                Toast.makeText(Another_Account_Find_Password.this,"메일을 보내지 못했습니다", Toast.LENGTH_LONG).show();

                            }

                            progressDialog.dismiss();
                        }
                    });
                }
            }
        }

    };

}

