package com.venndingal.loginsignup._AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.venndingal.loginsignup.Config;
import com.venndingal.loginsignup.RequestHandler;

import java.util.HashMap;

public class AddUser extends AsyncTask<Void, Void, String> {

    MyInterface mListener;
    ProgressDialog loading;
    Context _c;
    String _UserLevel, _AvatarPath, _FullName, _Gender, _Birthday, _Age, _Address, _MobileNo, _Email, _Username, _Password, _FromApp;
    String TAG = "AddUser";
    public AddUser(MyInterface mListener, Context c, String UserLevel,
                   String AvatarPath, String FullName, String Gender, String Birthday, String Age, String Address, String MobileNo, String Email,
                   String Username, String Password, String FromApp) {
        _c = c;
        _UserLevel = UserLevel;
        _AvatarPath = AvatarPath;
        _FullName = FullName;
        _Gender = Gender;
        _Birthday = Birthday;
        _Age = Age;
        _Address = Address;
        _MobileNo = MobileNo;
        _Email = Email;
        _Username = Username;
        _Password = Password;
        _FromApp = FromApp;


        Log.v(TAG, "_UserLevel >> " + _UserLevel);
        Log.v(TAG, "_AvatarPath >> " + _AvatarPath);
        Log.v(TAG, "_FullName >> " + _FullName);
        Log.v(TAG, "_Gender >> " + _Gender);
        Log.v(TAG, "_Birthday >> " + _Birthday);
        Log.v(TAG, "_Age >> " + _Age);
        Log.v(TAG, "_Address >> " + _Address);
        Log.v(TAG, "_MobileNo >> " + _MobileNo);
        Log.v(TAG, "_Email >> " + _Email);
        Log.v(TAG, "_Username >> " + _Username);
        Log.v(TAG, "_Password >> " + _Password);
        Log.v(TAG, "_FromApp >> " + _FromApp);

        this.mListener  = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loading = ProgressDialog.show(_c,"Loading Info","Please wait...",false,false);
            }
        });
    }

    protected String doInBackground(Void... args) {


        HashMap<String,String> params = new HashMap<>();
        params.put(Config.KEY_USERS_USERLEVEL,_UserLevel);
        params.put(Config.KEY_USERS_AVATARPATH,_AvatarPath);
        params.put(Config.KEY_USERS_FULLNAME,_FullName);
        params.put(Config.KEY_USERS_GENDER,_Gender);
        params.put(Config.KEY_USERS_BIRTHDAY,_Birthday);
        params.put(Config.KEY_USERS_AGE,_Age);
        params.put(Config.KEY_USERS_ADDRESS,_Address);
        params.put(Config.KEY_USERS_MOBILENO,_MobileNo);
        params.put(Config.KEY_USERS_EMAIL,_Email);
        params.put(Config.KEY_USERS_USERNAME,_Username);
        params.put(Config.KEY_USERS_PASSWORD,_Password);
        params.put(Config.KEY_USERS_FROMAPP,_FromApp);

        RequestHandler rh = new RequestHandler();
        String res = rh.sendPostRequest(Config.URL_ADD, params);

    return res;
    }

    protected void onPostExecute(String result) {
        // dismiss the dialog after getting all products
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
            }
        });
        if (mListener != null){
            mListener.myMethod(result);

        }else{
            mListener.myMethod(null);
        }

    }

    public interface MyInterface {
        public void myMethod(String result);
    }
}