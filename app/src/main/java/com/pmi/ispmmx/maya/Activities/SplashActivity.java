package com.pmi.ispmmx.maya.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.pmi.ispmmx.maya.R;
import com.pmi.ispmmx.maya.Utils.User.OperadorPreference;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        // setContentView(R.drawable.splash);






        pref = getSharedPreferences(OperadorPreference.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (pref.getBoolean(OperadorPreference.LOGGEDIN_SHARED_PREF, false)) {
            redireccionar(pref.getString(OperadorPreference.NOMBRE_ENTORNO_SHARED_PREF, ""));

        } else {
            logout();
        }

    }


    private void redireccionar(String entorno) {
        Class<?> c = null;
        if (entorno != null) {
            try {
                if (entorno != "Login") {
                    entorno = "Perfiles." + entorno;
                }
                c = Class.forName("com.pmi.ispmmx.maya.Activities." + entorno + "Activity");
                Intent intent = new Intent(SplashActivity.this, c);
                //intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logout();
            }
        }


    }


    private void logout() {

        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
        redireccionar("Login");

    }
}
