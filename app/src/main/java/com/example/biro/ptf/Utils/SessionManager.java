package com.example.biro.ptf.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.biro.ptf.Contract.Constants;
import com.example.biro.ptf.Network.Requests;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Activity _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "PTF";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String IS_COMPLETED = "IS_COMpleted";

    private static SessionManager instance = null;

    public static final String KEY_ID = "id";

    public static SessionManager getInstance(Activity context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }


    // Constructor
    private SessionManager(Activity context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String id) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing id in pref
        editor.putString(KEY_ID, id);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */


    public String getUserId() {


        // return user id
        return pref.getString(KEY_ID, null);
    }


    public void getLoggedInUser(final CallBack callBack) {


        String[] values = {"Bearer" + " " + getUserId()};
        Requests.getInstance(_context).getRequest(Constants.getLoggedUser.url, Constants.getLoggedUser.requestKeys, values, new Requests.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                callBack.onSuccess(result);
            }
        });


    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean getProfileStatus() {
        if (pref.getString(IS_COMPLETED, null).equals("false")) {
            return false;
        }
        else return true;

    }

    public void storeCompleteProfile(String status) {
        editor.putString(IS_COMPLETED, status);
    }

    public interface CallBack {
        void onSuccess(JSONObject result) throws JSONException;
    }
}