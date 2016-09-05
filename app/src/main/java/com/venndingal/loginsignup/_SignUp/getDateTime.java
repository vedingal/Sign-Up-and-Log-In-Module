package com.venndingal.loginsignup._SignUp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keng on 01/08/16.
 */
public class getDateTime {

    public String main(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }
}
