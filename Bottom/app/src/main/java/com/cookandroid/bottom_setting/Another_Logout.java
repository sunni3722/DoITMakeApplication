package com.cookandroid.bottom_setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Another_Logout extends AppCompatActivity {

    Button btnsignout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);


        btnsignout = findViewById(R.id.btn_logout);
        btnsignout.setOnClickListener(mLogoutListener);


    }
  
    //로그아웃
    Button.OnClickListener mLogoutListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Another_Logout_GlobalAuthHelper.accountLogout(getApplicationContext(), Another_Logout.this);
        }
    };

    public void directToMainActivity(Boolean result) {
        Intent intent = new Intent(Another_Logout.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}