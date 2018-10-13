package com.rijal.wkwkpedia;

/**
 * Created by User on 12/13/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "UserPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_LEVEL = "level";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String username,
                                   String level,
                                   int id
    ){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_LEVEL, level);
        editor.putInt(KEY_ID,id);
        editor.commit();
    }


    public int getIdUser(){
        return pref.getInt(KEY_ID,0);
    }

    public Boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            return false;
        }
        return true;
    }

    public int getlevelUser(){
        return pref.getInt(KEY_LEVEL,0);
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME,pref.getString(KEY_USERNAME,null));
        return user;
    }



    public boolean logoutUser(){
        editor.clear();
        editor.commit();

        if(isLoggedIn()){
            return false;
        }else{
            return true;
        }

    }

    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
