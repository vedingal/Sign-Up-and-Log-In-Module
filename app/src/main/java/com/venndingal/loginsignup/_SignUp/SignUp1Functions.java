package com.venndingal.loginsignup._SignUp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.venndingal.loginsignup.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by keng on 29/08/16.
 */
public class SignUp1Functions {

    Context _c;
    ImageButton _btn_avatar;
    EditText _et_fullName, _et_birthday, _et_age;
    Calendar myCalendar;

    public void main(Context c, ImageButton btn_avatar, EditText et_fullName, EditText et_birthday, EditText et_age){
        _c = c;
        _btn_avatar = btn_avatar;
        _et_fullName = et_fullName;
        _et_birthday = et_birthday;
        _et_age = et_age;

        _btn_avatar.setOnClickListener(doThisOnClick);
        _et_birthday.setOnClickListener(doThisOnClick);
        myCalendar = Calendar.getInstance();



    }


    public void setAvatar(){
        Intent intent = new Intent(_c, AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
        ((Activity) _c).startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    View.OnClickListener doThisOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_avatar:  setAvatar();
                    break;
                case R.id.et_birthday:
                    new DatePickerDialog(_c, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                default: break;
            }
        }
    };


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(year, monthOfYear,dayOfMonth);
        }

    };

    private void updateLabel(int year, int monthOfYear, int dayOfMonth) {

        String myFormat = "MMMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        _et_birthday.setText(sdf.format(myCalendar.getTime()));
        String age = Integer.toString(getAge(year, monthOfYear,dayOfMonth));
        _et_age.setText(age + " y/o");


    }

    public int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if(DOBmonth > currentMonth){
            --age;
        }
        else if(DOBmonth == currentMonth){
            if(DOBday > todayDay){
                --age;
            }
        }
        return age;
    }
}
