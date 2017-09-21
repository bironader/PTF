package com.example.biro.ptf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.biro.ptf.Network.FacebookApi;
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
    @BindView(R.id.mobile)
    EditText editMobile;
    @BindView(R.id.name)
    EditText editName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        facebookApi = FacebookApi.getInstance(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString();
                String mob = editMobile.getText().toString();
                if (mob.isEmpty() || mob.length() != 11) {

                    editMobile.setError(getString(R.string.mobileError));

                } else if (name.isEmpty())
                    editName.setError(getString(R.string.wrongName));

                else {
                    facebookApi.register(name, mob);
                    loginButton.registerCallback(FacebookApi.mCallbackManger, FacebookApi.facebookCallback);
                }

            }
        });

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends", "user_birthday"));


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FacebookApi.mCallbackManger.onActivityResult(requestCode, resultCode, data);
    }


}
