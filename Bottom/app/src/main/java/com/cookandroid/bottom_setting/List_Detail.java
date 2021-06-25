package com.cookandroid.bottom_setting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.cookandroid.bottom_setting.MainActivity.List_DB;

public class List_Detail extends AppCompatActivity {
    TextView tvTitle;
    TextView tvTerm_Start;
    TextView tvTerm_End;
    TextView tvPer;
    Button BtnModify;
    EditText edtDetail;
    SQLiteDatabase db = List_DB.getReadableDatabase();

    // 네이버 로그인 정보
    private String naver_id;

    // Naver intent로 ID & List_ID 받아오기
    String List_ID;
    String Title;
    String Term_Start;
    String Term_End;
    String Time_Start;
    String Time_End;
    String Level;
    String Category;
    String Detail;
    String Degree_Goal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);
        Intent intent = getIntent();
        tvTitle = findViewById(R.id.Title);
        tvTerm_Start = findViewById(R.id.Term_Start);
        tvTerm_End = findViewById(R.id.Term_End);
        edtDetail = findViewById(R.id.Detail);
        BtnModify = findViewById(R.id.Modify);

        // intent로 정보 받아 naver 로그인인지 확인
        Intent intent_main = new Intent(this.getIntent());
        naver_id = intent_main.getStringExtra("id");

        // Naver의 경우
        if (naver_id != null) {

            Title = intent_main.getStringExtra("Title");
            Term_Start = intent_main.getStringExtra("Term_Start");
            Term_End = intent_main.getStringExtra("Term_End");
            Time_Start = intent_main.getStringExtra("Time_Start");
            Time_End = intent_main.getStringExtra("Time_End");
            Level = intent_main.getStringExtra("Level");
            Category = intent_main.getStringExtra("Category");
            Detail = intent_main.getStringExtra("Detail");
            if (Detail == null) {
                Detail = "";
            }
            Degree_Goal = intent_main.getStringExtra("Degree_Goal");

            tvTitle.append(Title);
            tvTerm_Start.append(Term_Start);
            tvTerm_End.append(Term_End);
            edtDetail.append(Detail);
            BtnModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // WebServer와 통신하여 list ID 확인
                    String IP = getString(R.string.web_IP);

                    Detail = edtDetail.getText().toString();
                    // JSON 생성
                    JSONObject list = new JSONObject();     // JSON 오브젝트
                    try {

                        list.put("title", Title);    // list 부분 생성 시작
                        list.put("list_term_start", Term_Start);
                        list.put("list_term_end", Term_End);
                        list.put("list_time_start", Time_Start);
                        list.put("list_time_end", Time_End);
                        list.put("list_level", Level);
                        list.put("list_category", Category);
                        list.put("list_detail", Detail);
                        list.put("list_degree_goal", Degree_Goal);    // list 부분 생성 완료

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String modify_contents = list.toString();

                    // list contents update
                    ModifyData_list_Naver modify_data = new ModifyData_list_Naver(IP, null, getApplicationContext());
                    modify_data.execute(naver_id, modify_contents);

                    finish();
                }
            });

        } else {

            Title = intent.getExtras().getString("Title");

            Cursor cursor = db.rawQuery(List_DB_Make.SQL_SELECT_EACH + "'" + Title + "';", null);

            cursor.moveToNext();
            // List_No (INTEGER) 값 가져오기.
            //int List_No = cursor.getInt(0) ;

            // List_Title (INTEGER) 값 가져오기.
            final String List_Title = cursor.getString(0);

            // List_Term_Start (TEXT) 값 가져오기
            String List_Term_Start = cursor.getString(1);

            // List_Term_End (TEXT) 값 가져오기
            String List_Term_End = cursor.getString(2);

            // List_Time_Start (TEXT) 값 가져오기
            String List_Time_Start = cursor.getString(3);

            // List_Time_End (TEXT) 값 가져오기
            String List_Time_End = cursor.getString(4);

            // List_Level (TEXT) 값 가져오기
            String List_Level = cursor.getString(5);

            // List_Category (TEXT) 값 가져오기
            String List_Category = cursor.getString(6);

            // List_Degree_Goal (TEXT) 값 가져오기
            String List_Detail = cursor.getString(7);
            // List_Detail (TEXT) 값 가져오기
            String List_Degree_Goal = cursor.getString(8);

            tvTitle.append(Title);
            tvTerm_Start.append(List_Term_Start);
            tvTerm_End.append(List_Term_End);
            edtDetail.append(List_Detail);
            BtnModify.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = List_DB.getWritableDatabase();
                    String Detail = edtDetail.getText().toString();
                    String sqlUpdate = List_DB_Make.SQL_UPDATE + " SET DETAIL = " +
                            "\'" + Detail + "\'" +
                            " WHERE TITLE = " +
                            "'" + List_Title + "'";
                    db.execSQL(sqlUpdate);
                    db.close();
                    finish();
                }
            });
        }
    }
}
