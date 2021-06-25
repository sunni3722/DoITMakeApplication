package com.cookandroid.bottom_setting;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // Web Server Connect
    private static String TAG = "naver_profile";

    // Sending ID Info
    Bundle bundle = new Bundle(1);

    // Bottom Navigation
    private Home home = new Home();
    private List list = new List();
    private History history = new History();
    private Statistics statistics = new Statistics();
    private Another another = new Another();

    // Login NAVER Token
    String accessToken;

    //Profile
    JSONObject profile;
    String gender;
    String nickname;
    String id;
    String find = "";

    //DB 객체
    public static List_DB_Open List_DB;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DB 테이블 초기화
        init_tables();
        // 시작시 언어 변경하기
        String custom_lang = PreferenceManager.getString(getApplicationContext(), "lang");
        Locale locale = new Locale(custom_lang);
        Configuration config = new Configuration();
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // 시작시 테마 변경하기
        // !! 무조건 컨텐트 뷰보다 먼저 테마를 설정해줘야합니다 !!
        String custom_theme = PreferenceManager.getString(getApplicationContext(),"theme");
        if (custom_theme == "Brown") {
            setTheme(R.style.Brown);
        }
        else if (custom_theme == "Yellow") {
            setTheme(R.style.Yellow);
        }
        else if (custom_theme == "Dark") {
            setTheme(R.style.Dark);
        }
        else {
            setTheme(R.style.Basic);
        }


        String IP = getString(R.string.web_IP);

        // Naver 접근 토큰 받아오기
        Intent intent_main = new Intent(this.getIntent());
        accessToken = intent_main.getStringExtra("accessToken");
        if (accessToken != null) {
            // 토큰을 이용하여 프로필 정보 받아오기
            ApiMemberProfile apiMemberProfile = new ApiMemberProfile(accessToken);
            apiMemberProfile.start();
            // Web Server에서 요청을 받아 profile JSONObject 객체를 받아올 때까지 Main Thread를 잠시 멈춤
            try {
                apiMemberProfile.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            profile = apiMemberProfile.return_profile();
            // 받아온 정보를 PreferenceManager에 저장
            try {
                if (profile != null) {
                    id = (String) profile.getString("id");
                    gender = (String) profile.getString("gender");
                    nickname = (String) profile.getString("nickname");
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // 이용자의 고유 Naver ID 값을 이용해 정보 불러오기
            selectDatabase selectDatabase = new selectDatabase(IP, null, getApplicationContext());
            try {
                find = selectDatabase.execute(id).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 등록된 ID가 아닌 경우 Web Server에 아이디를 등록합니다.
            if (find.equals("null")) {
                InsertData insert_profile = new InsertData(IP, null, getApplicationContext());
                insert_profile.execute(id, nickname, gender);
                try {
                    selectDatabase selectDatabase2 = new selectDatabase(IP, null, getApplicationContext());
                    find = selectDatabase2.execute(id).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // 컨텐트 뷰 설정
        setContentView(R.layout.activity_main);

        if (accessToken != null) {
            // Bundle에 데이터 담아서 각 Fragment로 뿌려주기
            bundle.putString("id", id);
            list.setArguments(bundle);
            home.setArguments(bundle);
            history.setArguments(bundle);
            another.setArguments(bundle);
            statistics.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, home).commit();

        // 바텀네비게이션 뷰 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, home).commit();
                                return true;
                            case R.id.list:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, list).commit();
                                return true;
                            case R.id.history:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, history).commit();
                                return true;
                            case R.id.statistics:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, statistics).commit();
                                return true;
                            case R.id.another:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, another).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );
    }
    
    //DB 사용을 위해 table 초기화
    private void init_tables() {
        List_DB = new List_DB_Open(this);
        SQLiteDatabase db = List_DB.getWritableDatabase();
        //db.execSQL(List_DB_Make.SQL_DELETE) ; //DB 완전히 초기화
        List_DB.onCreate(db);
    }
}