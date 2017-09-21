package com.example.biro.ptf.Network;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.biro.ptf.R;
import com.example.biro.ptf.Utils.ApplicationController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by Biro on 7/4/2017.
 */

public class Requests {

    Activity context;
    private static Requests instance = null;
    public static boolean flag = false;


    public void setRequestResponse(JSONObject requestResponse) {
        Requests.requestResponse = requestResponse;
    }

    private static JSONObject requestResponse;


    private Requests(Activity context) {
        this.context = context;

    }

    public static Requests getInstance(Activity context) {
        if (instance == null) {
            instance = new Requests(context);

        }
        return instance;
    }


    public HashMap putParams(String keys[], String data[]) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < keys.length; i++) {
            params.put(keys[i], data[i]);
        }
        return params;
    }

    public void getRequest(String url, final VolleyCallback callback) {
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {


                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 400)
                    callback.onError(context.getString(R.string.registerError));


            }

        });

        ApplicationController.getInstance().addToRequestQueue(getReq);
    }

    public void getRequestWithHeader(String url, final String[] keys, final String[] values, final VolleyCallback callback) {

        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                try {
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                String body;
                //get status code here

                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    Toast.makeText(context, body, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
            }

        }) {

            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {

                return putParams(keys, values);
            }
        };

        ApplicationController.getInstance().addToRequestQueue(getReq);


    }

    public void postRequest(String url, String[] keys, String[] values, final VolleyCallback callback) {


        JsonObjectRequest postReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(putParams(keys, values)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    callback.onSuccess(response);
                    Log.d("response", response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            }

        });
        ApplicationController.getInstance().addToRequestQueue(postReq);

    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result) throws JSONException;

        void onError(String result);
    }

}
