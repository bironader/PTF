package com.example.biro.ptf.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.biro.ptf.CompleteActivity;
import com.example.biro.ptf.Contract.Constants;
import com.example.biro.ptf.Models.User;
import com.example.biro.ptf.Utils.SessionManager;
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


    public void login() {

        facebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                final String[] Values = {Constants.ObtainAccessToken.providerParam,
                        Constants.ObtainAccessToken.organizationCodeParm,
                        loginResult.getAccessToken().getToken()};
                Toast.makeText(context,loginResult.toString(),Toast.LENGTH_LONG).show();
//
                dialog = ProgressDialog.show(context, "",
                        "Loading. Please wait...", true);
                Requests.getInstance(context).postRequest(Constants.ObtainAccessToken.url, Constants.ObtainAccessToken.requestKeys, Values, new Requests.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        Toast.makeText(context,result.toString(),Toast.LENGTH_LONG).show();
//
                        try {
                            SessionManager.getInstance(context).createLoginSession(result.getString("access_token"));
                            SessionManager.getInstance(context).getLoggedInUser(new SessionManager.CallBack() {
                                @Override
                                public void onSuccess(JSONObject result) throws JSONException {

                                    JSONObject chidObj = result.getJSONObject("result");
                                    String status = chidObj.getString(Constants.userKeys.completeProfile);
                                    if (status.equals("false")) {
                                        startCompleteActivity();
                                        SessionManager.getInstance(context).storeCompleteProfile(status);
                                        dialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }


                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };


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
