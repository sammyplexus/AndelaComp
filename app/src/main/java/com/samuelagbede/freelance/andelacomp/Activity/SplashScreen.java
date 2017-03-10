package com.samuelagbede.freelance.andelacomp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Agbede Samuel D on 3/8/2017.
 */

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, SplashActivity.class));
    }
}
