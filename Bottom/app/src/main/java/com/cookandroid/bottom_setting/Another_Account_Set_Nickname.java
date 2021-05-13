package com.cookandroid.bottom_setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Another_Account_Set_Nickname extends AppCompatActivity {

    EditText editText_nickname;
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_account_set_nickname);

        editText_nickname = (EditText) findViewById(R.id.editText_nickname);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (editText_nickname.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Another.setNickname(editText_nickname.getText().toString());
                    Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
                    editText_nickname.setText(null);
                }
            }
        });
    }
}
