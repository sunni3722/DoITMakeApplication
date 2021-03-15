package com.cookandroid.bottom_setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class Another extends Fragment implements View.OnClickListener{
    // ProfileBox 텍스트 뷰
    TextView ProfileBox;

    // Bundle에서 ID정보 받아오기
    String id;
    String find = "";

    // Profile Info
    String nickname = null;
    String gender = null;

    public static Another newInstance() {
        return new Another();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                    nickname = naverProfile.getNickname();
                    gender = naverProfile.getGender();
                } catch (JSONException e) {
                    e.printStackTrace();
                    nickname = null;
                    gender = null;
                }
            }
            // Naver 아이디로 로그인하는 경우가 아닐 경우
            else {
                nickname = null;
                gender = null;
            }
        }

        View fv = inflater.inflate(R.layout.another, container, false);

        // ProfileBox에 Profile 정보 표시
        ProfileBox = fv.findViewById(R.id.textView);
        ProfileBox.setText(nickname + " / " + gender);

        return fv;
    }

    @Override
    public void onClick(View v) {

    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction userFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            userFt.replace(R.id.User, child);
            userFt.addToBackStack(null);
            userFt.commit();
        }
        FragmentTransaction systemFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            systemFt.replace(R.id.System, child);
            systemFt.addToBackStack(null);
            systemFt.commit();
        }
        FragmentTransaction developerFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            developerFt.replace(R.id.Developer, child);
            developerFt.addToBackStack(null);
            developerFt.commit();
        }

    }
}