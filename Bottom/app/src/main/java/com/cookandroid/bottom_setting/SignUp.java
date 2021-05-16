package com.cookandroid.bottom_setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button buttonJoin;
    private DatabaseReference databaseReference;
    private String id;
    private String password;
    private String name;
    private String level;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextName = (EditText) findViewById(R.id.editText_name);
        buttonJoin = (Button) findViewById(R.id.btn_join);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=editTextEmail.getText().toString();
                name=editTextName.getText().toString();
                password=editTextPassword.getText().toString();
                level="0";

                if (!id.equals("")&&!name.equals("")&&!password.equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    createUser(id, password, name);
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignUp.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    private void createUser(String ID, String pw, String NAME) {
        firebaseAuth.createUserWithEmailAndPassword(ID, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            uid=user.getUid();
                            writeNewPost(true);
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignUp.this, "가입할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

/*
    public void postFirebaseDatabase(boolean add, String ID, String name, String password){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            FirebasePost post = new FirebasePost(ID, name, password);
            postValues = post.toMap();
        }
        childUpdates.put("/id_list/" + ID, postValues);
        databaseReference.updateChildren(childUpdates);
    }*/


    private void writeNewPost(boolean add) {

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost user = new FirebasePost(id,name,password,level);
            postValues = user.toMap();
        }
        childUpdates.put(uid, postValues);
        databaseReference.updateChildren(childUpdates);
    }
}