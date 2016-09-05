package com.venndingal.loginsignup._AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.venndingal.loginsignup.Config;
import com.venndingal.loginsignup.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by keng on 05/09/16.
 */
public class getUserInfo extends AsyncTask<Void,Void,String> {

    Context _c;
    MyInterface mListener;
    String TAG = "getUserInfo";
    String _Username, _Password, _FromApp;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public getUserInfo(MyInterface mListener, Context c, String Username, String Password, String FromApp) {
        _c = c;
        _Username = Username;
        _Password = Password;
        _FromApp = FromApp;
        pref = c.getSharedPreferences("com.venndingal.loginsignup", Context.MODE_PRIVATE);
        editor = pref.edit();
        this.mListener  = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected String doInBackground(Void... args) {

        Log.v(TAG, "_Username >> " + _Username);
        Log.v(TAG, "_Password >> " + _Password);
        Log.v(TAG, "_FromApp >> " + _FromApp);


        RequestHandler rh = new RequestHandler();
        String s = rh.sendGetRequestParam(Config.URL_GET_USER_INFO,_Username);
        Log.v(TAG, "json.URL_GET_USER_INFO >> " + s);


            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(s);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                    for(int i = 0; i<result.length(); i++){
                        JSONObject jo = result.getJSONObject(i);

                        String id = jo.getString(Config.KEY_USERS_ID);
                        String UserLevel = jo.getString(Config.KEY_USERS_USERLEVEL);
                        String AvatarPath = jo.getString(Config.KEY_USERS_AVATARPATH);
                        String FullName = jo.getString(Config.KEY_USERS_FULLNAME);
                        String Gender = jo.getString(Config.KEY_USERS_GENDER);
                        String Birthday = jo.getString(Config.KEY_USERS_BIRTHDAY);
                        String Age = jo.getString(Config.KEY_USERS_AGE);
                        String Address = jo.getString(Config.KEY_USERS_ADDRESS);
                        String MobileNo = jo.getString(Config.KEY_USERS_MOBILENO);
                        String Email = jo.getString(Config.KEY_USERS_EMAIL);
                        String Username = jo.getString(Config.KEY_USERS_USERNAME);
                        String Password = jo.getString(Config.KEY_USERS_PASSWORD);
                        String FromApp = jo.getString(Config.KEY_USERS_FROMAPP);
                        String DateTimeCreated = jo.getString(Config.KEY_USERS_DATETIMECREATED);

                        Log.v(TAG, "id >> " + id);
                        Log.v(TAG, "UserLevel >> " + UserLevel);
                        Log.v(TAG, "AvatarPath >> " + AvatarPath);
                        Log.v(TAG, "FullName >> " + FullName);
                        Log.v(TAG, "Gender >> " + Gender);
                        Log.v(TAG, "Birthday >> " + Birthday);
                        Log.v(TAG, "Age >> " + Age);
                        Log.v(TAG, "Address >> " + Address);
                        Log.v(TAG, "MobileNo >> " + MobileNo);
                        Log.v(TAG, "Email >> " + Email);
                        Log.v(TAG, "Username >> " + Username);
                        Log.v(TAG, "Password >> " + Password);
                        Log.v(TAG, "FromApp >> " + FromApp);
                        Log.v(TAG, "DateTimeCreated >> " + DateTimeCreated);

                        editor.putString("currentUser_id", id);
                        editor.putString("currentUser_UserLevel", UserLevel);
                        editor.putString("currentUser_AvatarPath", AvatarPath);
                        editor.putString("currentUser_FullName", FullName);
                        editor.putString("currentUser_Gender", Gender);
                        editor.putString("currentUser_Birthday", Birthday);
                        editor.putString("currentUser_Age", Age);
                        editor.putString("currentUser_Address", Address);
                        editor.putString("currentUser_MobileNo", MobileNo);
                        editor.putString("currentUser_Email", Email);
                        editor.putString("currentUser_Username", Username);
                        editor.putString("currentUser_Password", Password);
                        editor.putString("currentUser_FromApp", FromApp);
                        editor.putString("currentUser_DateTimeCreated", DateTimeCreated);
                        editor.commit();
                    }
                    return "SUCCESS!";
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Something went wrong. \n\n " + e.toString());
                return "FAILED";
            }

    }

    protected void onPostExecute(String result) {

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
