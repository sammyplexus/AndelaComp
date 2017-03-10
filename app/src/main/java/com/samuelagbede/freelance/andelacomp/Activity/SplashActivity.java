package com.samuelagbede.freelance.andelacomp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.samuelagbede.freelance.andelacomp.Model.UsersModel;
import com.samuelagbede.freelance.andelacomp.R;
import com.samuelagbede.freelance.andelacomp.Volley.Singleton;
import com.wang.avi.Indicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Agbede Samuel D on 3/6/2017.
 */

public class SplashActivity extends Activity {
    private Indicator indicator;
    private Singleton singleton;
    private String url_string ="https://api.github.com/search/users?q=location:lagos+language:java&per_page=100";
    private RequestQueue requestQueue;
    private final String TAG = "request";
    private ArrayList<UsersModel> usersModelArrayList;
    UsersModel usersModel;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        usersModelArrayList = new ArrayList<>();
        singleton = Singleton.getInstance(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_string, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject)
            {
                try {
                    JSONObject jsonObject1;
                    jsonArray = (JSONArray) jsonObject.get("items");
                    Log.d("String", jsonArray.length()+"");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        usersModel = new UsersModel();
                        jsonObject1 = (JSONObject) jsonArray.get(i);
                        usersModel.setUser_id(jsonObject1.getInt("id"));
                        usersModel.setImage_url(jsonObject1.getString("avatar_url"));
                        usersModel.setProfile_url(jsonObject1.getString("html_url"));
                        usersModel.setUsername(jsonObject1.getString("login"));

                        usersModelArrayList.add(usersModel);
                    }
                    Intent move = new Intent(SplashActivity.this, MainActivity.class);
                    move.putExtra("serialize", usersModelArrayList);
                    startActivity(move);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                Toast.makeText(SplashActivity.this, "Network is probably poor. Check your connection and try again", Toast.LENGTH_LONG).show();
            }

        });
        jsonObjectRequest.setTag(TAG);
        singleton.getRequestQueue().add(jsonObjectRequest);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null)
        {
            requestQueue.cancelAll(TAG);
        }
    }
}
