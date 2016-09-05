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
public class getAllEmailANDUsername extends AsyncTask<Void,Void,String> {

    Context _c;
    MyInterface mListener;
    String[] arr_existingEmail, arr_existingUsername, arr_existingPassword;
    String[][] existingEmailANDUsername = null;
    String TAG = "getAllEmailANDUsername";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public getAllEmailANDUsername(MyInterface mListener, Context c) {
        _c = c;

        pref = c.getSharedPreferences("com.venndingal.loginsignup", Context.MODE_PRIVATE);
        editor = pref.edit();
        this.mListener  = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected String doInBackground(Void... params) {
        RequestHandler rh = new RequestHandler();
        String json = rh.sendGetRequest(Config.URL_GET_ALL);
        Log.v(TAG, "json.URL_GET_ALL >> " + json);

        boolean isSuccess = false;
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(json);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                if (result.length() == 0){
                    editor.putString("arr_existingEmail", "null");
                    editor.putString("arr_existingUsername", "null");
                    editor.putString("arr_existingPassword", "null");
                    editor.commit();
                    return "NONE";
                }else{
                    arr_existingEmail = new String[result.length()];
                    arr_existingUsername = new String[result.length()];
                    arr_existingPassword = new String[result.length()];
                    for(int i = 0; i<result.length(); i++){
                        JSONObject jo = result.getJSONObject(i);

                        String Email = jo.getString(Config.KEY_USERS_EMAIL);
                        String Username = jo.getString(Config.KEY_USERS_USERNAME);
                        String Password = jo.getString(Config.KEY_USERS_PASSWORD);

                        arr_existingEmail[i] = Email;
                        arr_existingUsername[i] = Username;
                        arr_existingPassword[i] = Password;

                        if (i == result.length()-1){

                            StringBuilder sb_arr_existingEmail = new StringBuilder();
                            for (int m = 0; m < arr_existingEmail.length; m++) {
                                sb_arr_existingEmail.append(arr_existingEmail[m]).append("#%");
                            }
                            editor.putString("arr_existingEmail", sb_arr_existingEmail.toString());
                            Log.v(TAG, "sb_arr_existingEmail.toString() >> " + sb_arr_existingEmail.toString());

                            StringBuilder sb_arr_existingUsername = new StringBuilder();
                            for (int k = 0; k < arr_existingEmail.length; k++) {
                                sb_arr_existingUsername.append(arr_existingUsername[k]).append("#%");
                            }
                            editor.putString("arr_existingUsername", sb_arr_existingUsername.toString());
                            Log.v(TAG, "sb_arr_existingUsername.toString() >> " + sb_arr_existingUsername.toString());

                            StringBuilder sb_arr_existingPassword = new StringBuilder();
                            for (int k = 0; k < arr_existingPassword.length; k++) {
                                sb_arr_existingPassword.append(arr_existingPassword[k]).append("#%");
                            }
                            editor.putString("arr_existingPassword", sb_arr_existingPassword.toString());
                            Log.v(TAG, "sb_arr_existingPassword.toString() >> " + sb_arr_existingPassword.toString());
                            editor.commit();

                            Log.v(TAG, "Registered users found.");



                        }else{

                        }

                    }
                    return "SUCCESS!";
                }



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
