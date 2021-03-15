package com.cookandroid.bottom_setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.bottom_setting.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Home extends Fragment {

    // Bundle에서 ID정보 받아오기
    String id;

    // Profile Info
    Integer level = 0;
    Double exp = 0.0;

    // Show Level & EXP
    ProgressBar exp_bar;
    TextView level_bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String find = "null";

        // Web Server Connect
        String IP = getString(R.string.web_IP);

        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getString("id");
        }
        if (id != null) {
            // 이용자의 고유 Naver ID 값을 이용해 정보 불러오기
            selectDatabase FindDatabase = new selectDatabase(IP, null, getContext());
            try {
                find = FindDatabase.execute(id).get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Naver 아이디로 로그인할 경우 실행
            if (!find.equals("null")) {
                Translate_JSON_NaverProfile naverProfile = null;
                try {
                    naverProfile = new Translate_JSON_NaverProfile(find);
                    level = naverProfile.getLevel();
                    exp = naverProfile.getExp();
                } catch (JSONException e) {
                    e.printStackTrace();
                    level = 0;
                    exp = 0.0;
                }
            }
            // Naver 아이디로 로그인하는 경우가 아닐 경우
            else {
                level = 0;
                exp = 0.0;
            }
        }
        // View 설정
        View view = inflater.inflate(R.layout.home, container, false);
        
        // Exp와 Level 표시
        exp_bar = view.findViewById(R.id.exp);
        if (exp != null) {
            exp_bar.setProgress(Integer.parseInt(String.valueOf(Math.round(exp))));
        }
        level_bar = view.findViewById(R.id.Level);
        if (level != null) {
            level_bar.setText(level.toString());
        }

        return view;
    }
}