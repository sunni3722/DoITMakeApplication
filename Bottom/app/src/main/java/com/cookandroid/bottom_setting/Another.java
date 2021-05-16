package com.cookandroid.bottom_setting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class Another extends Fragment implements View.OnClickListener{

    public static ImageView ProfilePhoto;
    public static Drawable photo;

    // ProfileBox 텍스트 뷰
    TextView ProfileBox;

    // Bundle에서 ID정보 받아오기
    String id;
    String find = "";

    // Profile Info
    public static String nickname = null;
    public static String gender = null;

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
                    photo = getResources().getDrawable(R.drawable.id);
                }
            }
            // Naver 아이디로 로그인하는 경우가 아닐 경우
            else {
                nickname = null;
                gender = null;
                photo = getResources().getDrawable(R.drawable.id);
            }
        }
        else if (FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            nickname = user.getEmail();
            gender = "";
            photo = getResources().getDrawable(R.drawable.id);
        }

        View fv = inflater.inflate(R.layout.another, container, false);

        // ProfilePhoto에 Profile 정보 표시
        ProfilePhoto = fv.findViewById(R.id.imageView);
        ProfilePhoto.setImageDrawable(photo);

        // ProfileBox에 Profile 정보 표시
        ProfileBox = fv.findViewById(R.id.textView);
        ProfileBox.setText(nickname + " / " + gender);

        return fv;
    }

    @Override
    public void onClick(View v) { }

    public static void setPhoto (Drawable newProfilePhoto){ photo = newProfilePhoto; }

    public static void setGender (String newGender){
        gender = newGender;
    }

    public static void setNickname (String newNickname){
        nickname = newNickname;
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