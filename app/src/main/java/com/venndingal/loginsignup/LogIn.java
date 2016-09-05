package com.venndingal.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.venndingal.loginsignup._AsyncTask.getAllEmailANDUsername;
import com.venndingal.loginsignup._AsyncTask.getUserInfo;

public class LogIn extends AppCompatActivity {

    Button btn_signUp, btn_logIn;
    Context c;
    String TAG = "LogIn";
    EditText et_username, et_password;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String prefsExistingUsernameThread, prefsExistingPasswordThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        c = this;
        btn_signUp = (Button) findViewById(R.id.btn_signUp);
        btn_logIn = (Button) findViewById(R.id.btn_logIn);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        pref = c.getSharedPreferences("com.venndingal.loginsignup", Context.MODE_PRIVATE);
        editor = pref.edit();
        prefsExistingUsernameThread = pref.getString("arr_existingUsername", null);
        Log.v(TAG, "prefsExistingUsernameThread >> " + prefsExistingUsernameThread);

        prefsExistingPasswordThread = pref.getString("arr_existingPassword", null);
        Log.v(TAG, "prefsExistingPasswordThread >> " + prefsExistingPasswordThread);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAllEmailANDUsername _getAllEmailANDUsername = new getAllEmailANDUsername(new getAllEmailANDUsername.MyInterface() {
                    @Override
                    public void myMethod(String result) {
                        Log.v(TAG, "result >> " + result);
                        if (result.equals("NONE")){
                            Log.v(TAG, "No users found.");
                        }else if (result.equals("SUCCESS!")){
                            Log.v(TAG, "Users are found.");
                        }else if (result.equals("FAILED")){
                            Log.v(TAG, "Failed to get users.");
                        }else {
                            Log.v(TAG, "Something went wrong.");
                        }

                        Intent in = new Intent(c, SignUp.class);
                        startActivity(in);
                    }
                }, c);
                _getAllEmailANDUsername.execute();
            }
        });

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAllEmailANDUsername _getAllEmailANDUsername = new getAllEmailANDUsername(new getAllEmailANDUsername.MyInterface() {
                    @Override
                    public void myMethod(String result) {

                        String inputUsername = et_username.getText().toString();
                        String inputPassword = et_password.getText().toString();

                        if (!inputUsername.equals("") && !inputPassword.equals("")){
                            matchUsernameANDPassword(inputUsername, inputPassword);
                        }else{
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(c, "Please fill in empty fields.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }, c);
                _getAllEmailANDUsername.execute();


            }
        });
    }

    private void matchUsernameANDPassword(String inputUsername, String inputPassword) {
        if (prefsExistingUsernameThread.equals("null")){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(c, "No registered users yet. Please sign up first.", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            boolean isValidUsername = false;
            int posOfUsername = 0;
            String[] existingUsername = prefsExistingUsernameThread.split("#%");
            for (int i = 0; i<existingUsername.length; i++){
                if (existingUsername[i].equals(inputUsername)){
                    isValidUsername = true;
                    posOfUsername = i;
                }else{}
                if (i == existingUsername.length - 1){
                    if (isValidUsername){
                        Log.v(TAG, "Entered username is valid.");
                        boolean isValidPassword = false;
                        int posOfPassword = 0;
                        String[] existingPassword = prefsExistingUsernameThread.split("#%");
                        for (int m = 0; m<existingPassword.length; m++){
                            if (existingPassword[m].equals(inputPassword)){
                                isValidPassword = true;
                                posOfPassword = m;
                            }else{}

                            if (m == existingPassword.length - 1){
                                if (isValidPassword){
                                    Log.v(TAG, "Entered password is valid.");
                                    if (posOfPassword == posOfUsername){
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(c, "MATCH!", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        getUserInfo _getUserInfo = new getUserInfo(new getUserInfo.MyInterface() {
                                            @Override
                                            public void myMethod(final String result) {
                                                Log.v(TAG, "/_getUserInfo/result >> " + result);

                                                if (result.equals("SUCCESS!")){
                                                    Intent in = new Intent(c, Home.class);
                                                    startActivity(in);
                                                    finish();
                                                }else{
                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(c, "Log In unsuccessful. \n\n" + result, Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }

                                            }
                                        }, c, inputUsername, inputPassword, getApplicationContext().getResources().getString(R.string.app_name));
                                        _getUserInfo.execute();

                                    }else{
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(c, "Username and Password did not match.", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }else {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(c, "Entered password is invalid.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }else{}
                        }
                    }else{
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(c, "Username entered is not a valid username", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }else{}
            }
        }
    }
}
