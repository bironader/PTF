package com.example.biro.ptf.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.biro.ptf.CompleteActivity;
import com.example.biro.ptf.Contract.Constants;
import com.example.biro.ptf.MainActivity;
import com.example.biro.ptf.Models.User;
import com.example.biro.ptf.R;
import com.example.biro.ptf.Utils.Session;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Biro on 3/29/2017.
 */

public class FacebookApi {

    private Activity context;

    private static FacebookApi instance = null;
    public static FacebookCallback<LoginResult> facebookCallback;
    public static CallbackManager mCallbackManger;
    private User newUser;
    ProgressDialog dialog;


    private FacebookApi(Activity context) {
        this.context = context;
        mCallbackManger = CallbackManager.Factory.create();
        newUser = new User();

    }


    public static FacebookApi getInstance(Activity context) {
        if (instance == null) {
            instance = new FacebookApi(context);
        }
        return instance;
    }


    public void register(final String name, final String mobile) {


        dialog = ProgressDialog.show(context, "",
                context.getString(R.string.progress), true);
        facebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                Toast.makeText(context, "hi", Toast.LENGTH_LONG).show();
                final String url = Constants.Register.url + Constants.Register.parmKeyProvider + "="
                        + Constants.Register.providerParam + "&"
                        + Constants.Register.parmKeyOrganizationCode + "=" + Constants.Register.organizationCodeParm + "&"
                        + Constants.Register.parmAccessToken + "=" + loginResult.getAccessToken().getToken() + "&"
                        + Constants.Register.parmMobile + "=" + mobile + "&"
                        + Constants.Register.parmName + "=" + name;

                Log.d("url", "onSuccess: " + url);


                Requests.getInstance(context).getRequest(url, new Requests.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        Log.d("url", "onSuccess: " + url);
                        Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
                        String access_token = result.getString(Constants.accessToken);
                        Session.getInstance(context).save(Constants.accessToken, access_token);
                        String isUserApproved = result.getString(Constants.isUserApproved);
                        Session.getInstance(context).save(Constants.isUserApproved, isUserApproved);
                        newUser.setMobilePhone(mobile);
                        dialog.dismiss();
                        startCompleteActivity();

                    }

                    @Override
                    public void onError(String result) {

                        final String[] values = {Constants.ObtainAccessToken.providerParam,
                                Constants.ObtainAccessToken.organizationCodeParm,
                                loginResult.getAccessToken().getToken()};


                        newUser.setMobilePhone(mobile);
                        dialog.dismiss();
                        login(values);

                    }
                });


            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "cancled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(context, R.string.serverFacebookError, Toast.LENGTH_LONG).show();
                Log.d("facebook server error", "onError: " + error.getMessage());

            }
        };


    }

    public void login(final String[] values) {

        Requests.getInstance(context).postRequest(Constants.ObtainAccessToken.url, Constants.ObtainAccessToken.requestKeys, values, new Requests.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                String access_token = result.getString(Constants.accessToken);
                Session.getInstance(context).save(Constants.accessToken, access_token);
                getLoggedIn(new String[]{"Bearer " + Session.getInstance(context).load(Constants.accessToken)});
            }

            @Override
            public void onError(String result) {

            }
        });
    }

    public void getLoggedIn(String[] values) {
        Requests.getInstance(context).getRequestWithHeader(Constants.getLoggedUser.url, Constants.getLoggedUser.requestKeys, values, new Requests.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {

                Log.d("logged in", "onSuccess: " + result);
                JSONObject child = result.getJSONObject("result");


                if (child.getBoolean("completeProfile"))
                    context.startActivity(new Intent(context, MainActivity.class));

                else
                    startCompleteActivity();


            }

            @Override
            public void onError(String result) {

            }
        });
    }

    public void startCompleteActivity() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String id = object.getString(Constants.userKeys.id);
                    String name = object.getString(Constants.userKeys.name);
                    String email = object.getString(Constants.userKeys.email);
                    String gender = object.getString(Constants.userKeys.gender);
                    String link = object.getString(Constants.userKeys.link);
                    JSONObject userPicParent = object.getJSONObject("picture");
                    JSONObject userPicChild = userPicParent.getJSONObject("data");
                    String userPicUrl = userPicChild.getString("url");
                    Log.d("user", object.toString());
                    newUser.setId(id);
                    newUser.setFull_name(name);
                    newUser.setEmail(email);
                    newUser.setProfile_link(link);
                    newUser.setGender(gender);
                    newUser.setProfile_pic_url(userPicUrl);
//                    dialog.dismiss();
                    context.startActivity(new Intent(context, CompleteActivity.class).putExtra(Constants.userKeys.key, newUser));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,gender,picture.width(999),email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }


}
