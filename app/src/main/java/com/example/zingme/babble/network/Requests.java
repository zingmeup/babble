package com.example.zingme.babble.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zingme.babble.R;
import com.example.zingme.babble.appdata.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Requests {
    private static Requests requests = null;
    StringRequest fetchRequest;


    public static Requests getRequests() {
        if (requests == null) requests = new Requests();
        return requests;
    }

    public StringRequest fetchData(String input, int featureNumber, final ArrayAdapter arrayAdapter) {

        String api="";
        if (featureNumber==1){
            api=AppData.getData().features.get(featureNumber).getApi().replace("[arg0]", input);
        }else if(featureNumber==0){
            api=AppData.getData().features.get(featureNumber).getApi()+input.replace(" ", "+");
        }else {
            api=AppData.getData().features.get(featureNumber).getApi()+input;
        }
        fetchRequest = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE", response);
                AppData.getData().resultList.clear();
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i = 0; i <jsonArray.length() ; i++) {
                        AppData.getData().resultList.add(jsonArray.getJSONObject(i).getString("word"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley-error", error.getMessage());

            }
        });
        return fetchRequest;
    }
}

