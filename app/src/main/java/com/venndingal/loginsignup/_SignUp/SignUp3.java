package com.venndingal.loginsignup._SignUp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by keng on 29/08/16.
 */
public class SignUp3 {
    SharedPreferences prefs;

    public void main(Context c){
        prefs = c.getSharedPreferences("com.venndingal.loginsignup", Context.MODE_PRIVATE);
        prefs.edit().putString("pos","3").apply();

    }
}
