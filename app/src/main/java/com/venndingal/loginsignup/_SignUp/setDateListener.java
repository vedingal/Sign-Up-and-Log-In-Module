package com.venndingal.loginsignup._SignUp;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

/**
 * Created by keng on 31/08/16.
 */
class setDateListener implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO Auto-generated method stub
        // getCalender();
        int mYear = year;
        int mMonth = monthOfYear;
        int mDay = dayOfMonth;
//        v.setText(new StringBuilder()
//                // Month is 0 based so add 1
//                .append(mMonth + 1).append("/").append(mDay).append("/")
//                .append(mYear).append(" "));
//        System.out.println(v.getText().toString());


    }
}