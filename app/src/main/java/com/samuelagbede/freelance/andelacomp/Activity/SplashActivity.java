package com.samuelagbede.freelance.andelacomp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Agbede Samuel D on 3/6/2017.
 */

public class SplashActivity extends Activity {
    private View indicator;
    private Singleton singleton;
    private String url_string ="https://api.github.com/search/users?q=location:lagos+language:java&per_page=100";
    private RequestQueue requestQueue;
    private final String TAG = "request";
    private ArrayList<UsersModel> usersModelArrayList;
    private TextView warning;
    private TextView aww_snap;
    private TextView loading_text;
    private Button retry;
    UsersModel usersModel;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        indicator = findViewById(R.id.avi);
        warning = (TextView) findViewById(R.id.warning);
        aww_snap = (TextView) findViewById(R.id.snap);
        loading_text = (TextView) findViewById(R.id.loading) ;
        retry = (Button) findViewById(R.id.retry);


        usersModelArrayList = new ArrayList<>();
        singleton = Singleton.getInstance(this);


        if(!isNetworkAvailable() || !isInternetAvailable())
        {
            showNeedful();
        }
        else
        {
            getData();
        }


        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetryClick();
                getData();
            }
        });




    }
    private  void onRetryClick(){
        loading_text.setVisibility(View.VISIBLE);
        indicator.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        aww_snap.setVisibility(View.GONE);
        retry.setVisibility(View.GONE);
    }

    private void showNeedful(){
        loading_text.setVisibility(View.GONE);
        indicator.setVisibility(View.GONE);
        warning.setVisibility(View.VISIBLE);
        aww_snap.setVisibility(View.VISIBLE);
        retry.setVisibility(View.VISIBLE);
    }

    boolean isInternetAvailable(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e)          { e.printStackTrace(); }
        return false;
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void getData(){
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
                showNeedful();
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
