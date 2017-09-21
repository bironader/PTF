package com.example.biro.ptf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biro.ptf.Contract.Constants;
import com.example.biro.ptf.Network.Requests;
import com.example.biro.ptf.Utils.Session;

import org.json.JSONException;
import org.json.JSONObject;


public class MeetingFrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getMeeting();
        return inflater.inflate(R.layout.fragment_meeting, container, false);
    }

    void getMeeting() {

        Requests.getInstance(getActivity()).getRequestWithHeader(Constants.getMeeting.url, Constants.getMeeting.requestKeys, new String[]{"Bearer " + Session.getInstance(getContext()).load(Constants.accessToken)}, new Requests.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                Log.d("result", "onSuccess: "+result);
            }

            @Override
            public void onError(String result) {

            }
        });
    }

}
