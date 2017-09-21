package com.example.biro.ptf.Utils;

import android.content.Context;

/**
 * Created by Biro on 9/18/2017.
 */

public class Session {

    private android.content.SharedPreferences sharedPreferences;
    private static Session instance = null;

    public static Session getInstance(Context context) {
        if (instance == null) {
            instance = new Session(context);
        }
        return instance;
    }
    private  Session(Context context) {

        this.sharedPreferences = context.getSharedPreferences("PTF", Context.MODE_PRIVATE);
    }

    public void save(String tag,String data) {

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag,data);
        editor.apply();


    }

    public String load(String tag)
    {
        return sharedPreferences.getString(tag,"NULL");
    }
}
