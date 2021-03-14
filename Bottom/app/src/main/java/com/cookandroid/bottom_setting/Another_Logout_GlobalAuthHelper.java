package com.cookandroid.bottom_setting;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class Another_Logout_GlobalAuthHelper {

    public static void accountLogout(Context context, Another_Logout activity) {
        if (OAuthLoginState.OK.equals(OAuthLogin.getInstance().getState(context))) {
            OAuthLogin.getInstance().logout(context);

            Toast.makeText(context, "네이버 로그아웃", Toast.LENGTH_SHORT).show();

            activity.directToMainActivity(true);
        } else if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();

            Toast.makeText(context, "파이어베이스 로그아웃", Toast.LENGTH_SHORT).show();

            activity.directToMainActivity(true);
        }
    }
}
