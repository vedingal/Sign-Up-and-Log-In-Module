package com.venndingal.loginsignup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.venndingal.loginsignup._AsyncTask.AddUser;
import com.venndingal.loginsignup._AsyncTask.getAllEmailANDUsername;
import com.venndingal.loginsignup._SignUp.SignUp1Functions;
import com.venndingal.loginsignup._SignUp.getDateTime;

import java.io.File;
import java.util.ArrayList;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class SignUp extends AppCompatActivity {

    /*********
     * work only for Dedicated IP
     ***********/
    static final String FTP_HOST = "enterFTPHostHere";

    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "enterFTPUsersHere";

    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "enterFTPPassHere";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    String TAG = "SignUp";

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    static SharedPreferences prefs;
    static Context c;
    Button btn_prev, btn_next;

    //SignUp1Functions
    static ImageButton btn_avatar;
    static EditText et_fullName;
    static EditText et_birthday;
    static EditText et_age;
    static RadioGroup radioGender;


    static RadioGroup radioCivilStatus;
    static EditText et_address;
    static EditText et_mobileNo;
    static EditText et_email;
    static Button btn_verifyEmail;

    //SignUp3Functions
    static EditText et_username;
    static EditText et_pass;
    static EditText et_confpass;

    String path = "";
    String finalAvatarPath, finalFullName, finalGender, finalBirthday, finalAge;
    String finalCivilStatus, finalAddress, finalMobileNo, finalEmail;
    String finalUsername, finalPassword;
    String finalFromApp;
    String finalFileName;
    String finalUserLevel = "user";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String prefsExistingEmailThread, prefsExistingUsernameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        c = this;

        finalFromApp = getApplicationContext().getResources().getString(R.string.app_name);

        pref = c.getSharedPreferences("com.venndingal.loginsignup", Context.MODE_PRIVATE);
        editor = pref.edit();
        prefsExistingEmailThread = pref.getString("arr_existingEmail", null);
        Log.v(TAG, "prefsExistingEmailThread >> " + prefsExistingEmailThread);

        prefsExistingUsernameThread = pref.getString("arr_existingUsername", null);
        Log.v(TAG, "prefsExistingUsernameThread >> " + prefsExistingUsernameThread);

        btn_prev = (Button) findViewById(R.id.btn_prev);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String actualPos = Integer.toString(mViewPager.getCurrentItem());
                Log.v(TAG, "onCreate/btn_next/mViewPager.getCurrentItem() >>> " + actualPos);

                if (actualPos.equals("0")) {

                    if (signup1IsComplete()){
                        finalAvatarPath = path;
                        Log.w(TAG, "path >> " + path);
                        finalFullName = et_fullName.getText().toString();
                        if (radioGender.getCheckedRadioButtonId() == R.id.rb_male){
                            finalGender = "Male";
                        }else{
                            finalGender = "Female";
                        }
                        finalBirthday = et_birthday.getText().toString();
                        finalAge = et_age.getText().toString();

                        askIfSure(actualPos);

                        //mViewPager.setCurrentItem(1);
                        //btn_prev.setVisibility(View.VISIBLE);
                    }else{

                    }


                    //new uploadImage().execute();
                } else if (actualPos.equals("1")) {


                    if (signup2IsComplete()){

                        if (radioCivilStatus.getCheckedRadioButtonId() == R.id.rb_single){
                            finalCivilStatus = "Single";
                        }else if (radioCivilStatus.getCheckedRadioButtonId() == R.id.rb_married){
                            finalCivilStatus = "Married";
                        }else if (radioCivilStatus.getCheckedRadioButtonId() == R.id.rb_widowed){
                            finalCivilStatus = "Widowed";
                        }

                        finalAddress = et_address.getText().toString();
                        finalMobileNo = et_mobileNo.getText().toString();
                        finalEmail = et_email.getText().toString();

                        if (prefsExistingEmailThread.equals("null")){
                            askIfSure(actualPos);
                        }else{
                            boolean emailExists = false;
                            String[] existingEmail = pref.getString("arr_existingEmail", null).split("#%");
                            for (int i = 0; i < existingEmail.length; i++){
                                if (existingEmail[i].equals(finalEmail)){
                                    emailExists = true;
                                }else{

                                }

                                if (i == existingEmail.length - 1){
                                    if (emailExists){
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        et_email.setError("Email already exists.");
                                                    }
                                                });

                                            }
                                        });
                                    }else{
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                et_email.setError(null);
                                            }
                                        });
                                        askIfSure(actualPos);
                                    }
                                }
                            }
                        }



                    }else{

                    }

                    //mViewPager.setCurrentItem(2);
                    //btn_next.setText("SUBMIT");
                } else if (actualPos.equals("2")) {

                    if (signup3IsComplete()){

                        finalUsername = et_username.getText().toString();
                        finalPassword = et_pass.getText().toString();

                        if (prefsExistingUsernameThread.equals("null")){
                            askIfSure(actualPos);
                        }else{
                            boolean usernameExists = false;
                            String[] existingUsername = pref.getString("arr_existingUsername", null).split("#%");
                            for (int i = 0; i < existingUsername.length; i++){
                                if (existingUsername[i].equals(finalUsername)){
                                    usernameExists = true;
                                }else{

                                }

                                if (i == existingUsername.length - 1){
                                    if (usernameExists){
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        et_username.setError("Username already exists.");
                                                    }
                                                });

                                            }
                                        });
                                    }else{
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                et_username.setError(null);
                                            }
                                        });
                                        askIfSure(actualPos);
                                    }
                                }
                            }
                        }

                    }else{

                    }

                    //mViewPager.setCurrentItem(0);
                    //btn_next.setText("NEXT");
                    //btn_prev.setVisibility(View.INVISIBLE);
                }
            }


        });

        /*
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String actualPos = Integer.toString(mViewPager.getCurrentItem());
                Log.v(TAG, "onCreate/btn_prev/mViewPager.getCurrentItem() >>> " + actualPos);

                if (actualPos.equals("0")) {
                    //mViewPager.setCurrentItem(1);
                    btn_prev.setVisibility(View.INVISIBLE);
                } else if (actualPos.equals("1")) {
                    mViewPager.setCurrentItem(0);
                    btn_prev.setVisibility(View.INVISIBLE);
                    btn_next.setText("NEXT");
                } else if (actualPos.equals("2")) {
                    btn_prev.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(1);
                    btn_next.setText("NEXT");
                }
            }
        });
        */


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    private void askIfSure(final String actualPos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage("Are these information final? You can not edit these once you're in the next page.");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (actualPos.equals("0")) {


                            mViewPager.setCurrentItem(1);
                            //btn_prev.setVisibility(View.VISIBLE);
                            //new uploadImage().execute();
                        } else if (actualPos.equals("1")) {
                            mViewPager.setCurrentItem(2);
                            btn_next.setText("SUBMIT");
                        } else if (actualPos.equals("2")) {
                            new uploadImage().execute();
                        }
                    }
                });

        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SignUp Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.venndingal.loginsignup/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SignUp Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.venndingal.loginsignup/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class uploadImage extends AsyncTask<Void, Void, Void> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    loading = ProgressDialog.show(c,"Loading Info","Please wait...",false,false);
                }
            });

        }

        @Override
        protected Void doInBackground(Void... voids) {
            FTPClient client = new FTPClient();
            try {
                client.connect(FTP_HOST, 21);
                client.login(FTP_USER, FTP_PASS);
                client.setType(FTPClient.TYPE_BINARY);
                client.changeDirectory("/public_html/api/tuts/LogInSignUp/uploads");
                client.upload(new File(path), new MyTransferListener());

                String currentFileName = path.substring(path.lastIndexOf("/"), path.length());
                currentFileName = currentFileName.substring(1);
                Log.i("Current file name", currentFileName);
                String text = new getDateTime().main().replace(" ","T");
                File directory = new File(path.replace(currentFileName, ""));
                File from      = new File(directory, currentFileName);
                File to        = new File(directory, text.trim() + ".png");
                from.renameTo(to);
                Log.i("Directory is", directory.toString());
                Log.i("Default path is", path.toString());
                Log.i("From path is", from.toString());
                Log.i("To path is", to.toString());
                client.rename("/public_html/api/tuts/LogInSignUp/uploads/" + currentFileName, "/public_html/api/tuts/LogInSignUp/uploads/" + text.trim() + ".png");
                finalFileName = text.trim() + ".png";

                Log.w(TAG, "finalUserLevel >> " + finalUserLevel);
                Log.w(TAG, "finalAvatarPath >> " + finalFileName);
                Log.w(TAG, "finalFullName >> " + finalFullName);
                Log.w(TAG, "finaleGender >> " + finalGender);
                Log.w(TAG, "finalBirthday >> " + finalBirthday);
                Log.w(TAG, "finalAge >> " + finalAge.replace(" y/o", ""));
                Log.w(TAG, "finalAddress >> " + finalAddress);
                Log.w(TAG, "finalMobileNo >> " + finalMobileNo);
                Log.w(TAG, "finalEmail >> " + finalEmail);
                Log.w(TAG, "finalUsername >> " + finalUsername);
                Log.w(TAG, "finalPassword >> " + finalPassword);
                Log.w(TAG, "finalFromApp >> " + finalFromApp);

                Log.v(TAG, "uploadImage >> SUCCESS!!");


            } catch (Exception e) {
                Log.v(TAG, "uploadImage >> Failed. \n\n" + e.getMessage());
                e.printStackTrace();
                try {
                    client.disconnect(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    loading.dismiss();
                }
            });
            AddUser addUser = new AddUser(new AddUser.MyInterface() {
                @Override
                public void myMethod(String result) {
                    Toast.makeText(c,result,Toast.LENGTH_LONG).show();

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

                    ((Activity)c).finish();

                    Intent in = new Intent(c, LogIn.class);
                    startActivity(in);
                }

            }, c, finalUserLevel, finalFileName, finalFullName, finalGender, finalBirthday, finalAge.replace(" y/o", ""), finalAddress,
                    finalMobileNo, finalEmail, finalUsername, finalPassword, finalFromApp);
            addUser.execute();
        }
    }




    private boolean signup1IsComplete() {

        if (!et_fullName.getText().toString().equals("") &&
                radioGender.getCheckedRadioButtonId() != -1 &&
                !et_birthday.getText().toString().equals("") &&
                !path.equals("")) {
            Log.v(TAG, "signup1IsComplete/ Everything is okay. Proceed");
            return true;
        } else {
            Log.v(TAG, "signup1IsComplete/ There are still empty fields.");
            Toast.makeText(c, "Please fill in all fields.", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private boolean signup2IsComplete() {

        if (!et_address.getText().toString().equals("") &&
                radioCivilStatus.getCheckedRadioButtonId() != -1 &&
                !et_mobileNo.getText().toString().equals("") &&
                !et_email.getText().toString().equals("") ) {
            Log.v(TAG, "signup2IsComplete/ Everything is okay. Proceed");
            return true;
        } else {
            Log.v(TAG, "signup12sComplete/ There are still empty fields.");
            Toast.makeText(c, "Please fill in all fields.", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private boolean signup3IsComplete() {

        if (!et_username.getText().toString().equals("") &&
                !et_pass.getText().toString().equals("") &&
                !et_confpass.getText().toString().equals("") ) {

                if (et_pass.getText().toString().equals(et_confpass.getText().toString())){
                    Log.v(TAG, "signup3IsComplete/ Everything is okay. Proceed");
                    return true;
                }else{
                    Log.v(TAG, "signup13sComplete/ Passwords did not match.");
                    Toast.makeText(c, "Passwords did not match.", Toast.LENGTH_LONG).show();
                    return false;
                }


        } else {
            Log.v(TAG, "signup13sComplete/ There are still empty fields.");
            Toast.makeText(c, "Please fill in all fields.", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String pos = Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER));

            View rootView = null;

            switch (pos) {
                case "1":
                    rootView = inflater.inflate(R.layout.signup1, container, false);
                    btn_avatar = (ImageButton) rootView.findViewById(R.id.btn_avatar);
                    et_fullName = (EditText) rootView.findViewById(R.id.et_fullName);
                    et_birthday = (EditText) rootView.findViewById(R.id.et_birthday);
                    et_age = (EditText) rootView.findViewById(R.id.et_age);
                    radioGender = (RadioGroup) rootView.findViewById(R.id.radioGender);
                    new SignUp1Functions().main(c, btn_avatar, et_fullName, et_birthday, et_age);
                    break;
                case "2":
                    rootView = inflater.inflate(R.layout.signup2, container, false);
                    radioCivilStatus = (RadioGroup) rootView.findViewById(R.id.radioCivilStatus);
                    et_address = (EditText) rootView.findViewById(R.id.et_address);
                    et_mobileNo = (EditText) rootView.findViewById(R.id.et_mobileNo);
                    et_email = (EditText) rootView.findViewById(R.id.et_email);
                    btn_verifyEmail = (Button) rootView.findViewById(R.id.btn_verifyEmail);
                    break;
                case "3":
                    rootView = inflater.inflate(R.layout.signup3, container, false);
                    et_username = (EditText) rootView.findViewById(R.id.et_username);
                    et_pass = (EditText) rootView.findViewById(R.id.et_pass);
                    et_confpass = (EditText) rootView.findViewById(R.id.et_confpass);
                    break;
                default:
                    break;
            }
            return rootView;
        }

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            btn_avatar.setBackgroundColor(ContextCompat.getColor(c, R.color.colorAccent));
            btn_avatar.setImageURI(Uri.parse(images.get(0).path.toString()));
            path = Uri.parse(images.get(0).path.toString()).toString();
            Log.v(TAG, "onActivityResult/path >> " + path);
        }
    }


    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getBaseContext(), " Upload Started ...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void transferred(final int length) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getBaseContext(), "Transferring file..." + length, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void completed() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getBaseContext(), "Complete!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void aborted() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getBaseContext(), "File transfer aborted. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void failed() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                   // System.out.println("Upload Failed.");
                }
            });
        }

    }
}
