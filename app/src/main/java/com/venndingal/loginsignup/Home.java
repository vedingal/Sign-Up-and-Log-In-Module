package com.venndingal.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Home extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context c;
    String id, UserLevel, AvatarPath, FullName, Gender, Birthday, Age, Address, MobileNo, Email, Username, Password, FromApp, DateTimeCreated;
    String TAG = "Home";
    EditText et_id, et_UserLevel, et_AvatarPath, et_FullName, et_Gender, et_Birthday,
            et_Age, et_Address, et_MobileNo, et_Email, et_Username, et_Password, et_FromApp, et_DateTimeCreated;
    ImageView img_avatar;
    Button btn_next;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        c = this;

        pref = c.getSharedPreferences("com.venndingal.loginsignup", Context.MODE_PRIVATE);
        editor = pref.edit();

        img_avatar = (ImageView) findViewById(R.id.img_avatar);
        et_id = (EditText) findViewById(R.id.et_id);
        et_UserLevel = (EditText) findViewById(R.id.et_UserLevel);
        et_AvatarPath = (EditText) findViewById(R.id.et_AvatarPath);
        et_FullName = (EditText) findViewById(R.id.et_FullName);
        et_Gender = (EditText) findViewById(R.id.et_Gender);
        et_Birthday = (EditText) findViewById(R.id.et_Birthday);
        et_Age = (EditText) findViewById(R.id.et_Age);
        et_Address = (EditText) findViewById(R.id.et_Address);
        et_MobileNo = (EditText) findViewById(R.id.et_MobileNo);
        et_Email = (EditText) findViewById(R.id.et_Email);
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Password = (EditText) findViewById(R.id.et_Password);
        et_FromApp = (EditText) findViewById(R.id.et_FromApp);
        et_DateTimeCreated = (EditText) findViewById(R.id.et_DateTimeCreated);
        btn_next = (Button) findViewById(R.id.btn_next);
        textView = (TextView) findViewById(R.id.textView);

        btn_next.setVisibility(View.INVISIBLE);
        textView.setText("Home");

        id = pref.getString("currentUser_id", null);
        UserLevel = pref.getString("currentUser_UserLevel", null);
        AvatarPath = pref.getString("currentUser_AvatarPath", null);
        FullName = pref.getString("currentUser_FullName", null);
        Gender = pref.getString("currentUser_Gender", null);
        Birthday = pref.getString("currentUser_Birthday", null);
        Age = pref.getString("currentUser_Age", null);
        Address = pref.getString("currentUser_Address", null);
        MobileNo = pref.getString("currentUser_MobileNo", null);
        Email = pref.getString("currentUser_Email", null);
        Username = pref.getString("currentUser_Username", null);
        Password = pref.getString("currentUser_Password", null);
        FromApp = pref.getString("currentUser_FromApp", null);
        DateTimeCreated = pref.getString("currentUser_DateTimeCreated", null);

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

        new DownloadImageTask((ImageView) findViewById(R.id.img_avatar))
                .execute("http://api.venndingal.com/tuts/LogInSignUp/uploads/" + AvatarPath);

        et_id.setText(id);
        et_UserLevel.setText(UserLevel);
        et_AvatarPath.setText(AvatarPath);
        et_FullName.setText(FullName);
        et_Gender.setText(Gender);
        et_Birthday.setText(Birthday);
        et_Age.setText(Age);
        et_Address.setText(Address);
        et_MobileNo.setText(MobileNo);
        et_Email.setText(Email);
        et_Username.setText(Username);
        et_Password.setText(Password);
        et_FromApp.setText(FromApp);
        et_DateTimeCreated.setText(DateTimeCreated);

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent in = new Intent(c, Splash.class);
        startActivity(in);
        super.onBackPressed();
    }
}
