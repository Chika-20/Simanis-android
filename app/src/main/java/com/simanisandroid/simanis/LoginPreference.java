package com.simanisandroid.simanis;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginPreference {

    // Shared preferences file name
    private static final String PREF_NAME = "SIMANIS";
    private static final String IS_LOGIN_IN = "isLoginIn";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public LoginPreference(Context context) {
        this._context = context;
        pref =_context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLoginStatus(boolean isLoginIn) {
        editor.putBoolean(IS_LOGIN_IN, isLoginIn);
        editor.commit();
    }

    public boolean isLoginIn() {
        return pref.getBoolean(IS_LOGIN_IN, false);
    }
}
