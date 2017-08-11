package com.example.biro.ptf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.biro.ptf.Network.FacebookApi;
import com.example.biro.ptf.Utils.SessionManager;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    FacebookApi facebookApi;
    @BindView(R.id.login_button)
    LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        facebookApi = FacebookApi.getInstance(this);


        facebookApi.login();

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends", "user_birthday"));
        loginButton.registerCallback(FacebookApi.mCallbackManger, FacebookApi.facebookCallback);


    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (AccessToken.getCurrentAccessToken() != null)
//        {
//            LoginManager.getInstance().logOut();
//        }
////        if (!SessionManager.getInstance(this).getUserId().isEmpty())
////        {
////            if(SessionManager.getInstance(this).getProfileStatus())
////            {
////
////            }
////        }
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FacebookApi.mCallbackManger.onActivityResult(requestCode, resultCode, data);
    }
}
