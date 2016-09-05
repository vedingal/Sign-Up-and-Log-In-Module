package com.venndingal.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.venndingal.loginsignup._AsyncTask.getAllEmailANDUsername;

public class Splash extends AppCompatActivity {

    Context c;
    String TAG = "Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        c = this;
        SharedPreferences pref = c.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        getAllEmailANDUsername _getAllEmailANDUsername = new getAllEmailANDUsername(new getAllEmailANDUsername.MyInterface() {
            @Override
            public void myMethod(String result) {
                Log.v(TAG, "result >> " + result);
                if (result.equals("NONE")){
                    Log.v(TAG, "No users found.");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(c,"No registered users found. Please sign up first.", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent in = new Intent(c, SignUp.class);
                    startActivity(in);
                }else if (result.equals("SUCCESS!")){
                    Log.v(TAG, "Users are found.");
                    Intent in = new Intent(c, LogIn.class);
                    startActivity(in);
                }else if (result.equals("FAILED")){
                    Log.v(TAG, "Failed to get users.");
                }else {
                    Log.v(TAG, "Something went wrong.");
                }
            }
        }, c);
        _getAllEmailANDUsername.execute();
    }
}
