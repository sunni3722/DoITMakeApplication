package com.cookandroid.bottom_setting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class Another_Account extends AppCompatActivity {

    ListView listview;
    static final String[] LIST_ACCOUNT = {"프로필 사진 변경", "닉네임 변경", "성별 변경", "비밀번호 재설정", "회원 탈퇴"};
    private FirebaseAuth firebaseAuth ;
    ProgressDialog progressDialog;
    EditText email;

    int GenderSelectItem;
    android.app.AlertDialog.Builder GenderDlg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_account);


        firebaseAuth = FirebaseAuth.getInstance();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_ACCOUNT) ;

        listview = (ListView) findViewById(R.id.listview3);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                switch (position) {

                    // 프로필 사진 변경
                    case 0:
                        Intent set_profile_photo = new Intent(Another_Account.this, Another_Account_Set_Photo.class);
                        startActivity(set_profile_photo);
                        break;

                    // 닉네임 변경
                    case 1:
                        Intent set_profile_nickname = new Intent(Another_Account.this, Another_Account_Set_Nickname.class);
                        startActivity(set_profile_nickname);
                        break;

                    // 성별 변경
                    case 2:
                        final CharSequence[] ArrGenderDlg = {"남자", "여자"};

                        GenderDlg = new android.app.AlertDialog.Builder(Another_Account.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                        // 성별 다이얼로그 창 설정
                        GenderDlg.setTitle(getString(R.string.SelectGender))
                                .setSingleChoiceItems(ArrGenderDlg, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        GenderSelectItem = which;
                                    }
                                })
                                .setNeutralButton(getString(R.string.Select), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(GenderSelectItem == 0) {
                                            Toast.makeText(getApplicationContext(), "M", Toast.LENGTH_LONG).show();
                                            Another.setGender("M");

                                        }
                                        else if(GenderSelectItem == 1) {
                                            Toast.makeText(getApplicationContext(), "F", Toast.LENGTH_LONG).show();
                                            Another.setGender("F");
                                        }
                                    }
                                })
                                .setCancelable(true)
                                .show();
                        break;

                    // 비밀번호 재설정
                    case 3:
                        if (firebaseAuth.getCurrentUser() != null) {
                            Intent find_password = new Intent(Another_Account.this, Another_Account_Find_Password.class);
                            startActivity(find_password);
                            finish();
                        } else if (OAuthLoginState.OK.equals(OAuthLogin.getInstance().getState(getApplicationContext()))){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = Uri.parse("http://www.naver.com");
                            intent.setData(uri);
                            startActivity(intent);
                            Toast.makeText(Another_Account.this, " 네이버로 이동합니다.",Toast.LENGTH_LONG).show();
                        }
                        break;

                    // 회원 탈퇴
                    case 4:
                        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Another_Account.this);
                        alert_confirm.setMessage("정말 계정을 삭제하시겠습니까?").setCancelable(false).setPositiveButton(("확인"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (firebaseAuth.getCurrentUser() != null) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Another_Account.this, " 계정이 삭제되었습니다.",Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                                        }
                                    });
                                } else if (OAuthLoginState.OK.equals(OAuthLogin.getInstance().getState(getApplicationContext()))){
                                    OAuthLogin.getInstance().logoutAndDeleteToken(getApplicationContext());
                                    Toast.makeText(Another_Account.this, " 계정이 삭제되었습니다.",Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                            }
                        });
                        alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Another_Account.this, "취소", Toast.LENGTH_LONG).show();
                            }
                        });
                        alert_confirm.show();
                        break;
                        
                }
            }
        }) ;

        progressDialog = new ProgressDialog(this);

    }

}
