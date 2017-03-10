package com.samuelagbede.freelance.andelacomp.Volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Agbede Samuel D on 3/6/2017.
 */

public class Singleton {
    private static Singleton mInstance = null;
    private static RequestQueue requestQueue = null;
    Context context;

    private Singleton(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();
    }


    public static Singleton getInstance(Context context){
        if (mInstance == null)
        {
            mInstance = new Singleton(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }


}
