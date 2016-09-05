package com.venndingal.loginsignup;

/**
 * Created by keng on 04/09/16.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://api.venndingal.com/tuts/LogInSignUp/addUsers.php";
    public static final String URL_GET_ALL = "http://api.venndingal.com/tuts/LogInSignUp/getAllUsers.php";
    public static final String URL_GET_USER_INFO = "http://api.venndingal.com/tuts/LogInSignUp/getUserInfo.php?Username=";
   // public static final String URL = ""

    //Keys that will be used to send the request to php scripts
    public static final String KEY_USERS_ID = "id";
    public static final String KEY_USERS_USERLEVEL = "UserLevel";
    public static final String KEY_USERS_AVATARPATH = "AvatarPath";
    public static final String KEY_USERS_FULLNAME= "FullName";
    public static final String KEY_USERS_GENDER = "Gender";
    public static final String KEY_USERS_BIRTHDAY = "Birthday";
    public static final String KEY_USERS_AGE = "Age";
    public static final String KEY_USERS_ADDRESS = "Address";
    public static final String KEY_USERS_MOBILENO = "MobileNo";
    public static final String KEY_USERS_EMAIL = "Email";
    public static final String KEY_USERS_USERNAME = "Username";
    public static final String KEY_USERS_PASSWORD = "Password";
    public static final String KEY_USERS_FROMAPP = "FromApp";
    public static final String KEY_USERS_DATETIMECREATED = "DateTimeCreated";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
}
