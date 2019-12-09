package com.example.farmogoapp.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

public class Session {

    private static final String KEY_API_AUTH = "KEY_API_AUTH";
    private static final String KEY_NAME = "KEY_NAME";
    private static final String KEY_EMAIL = "KEY_EMAIL";

    private static final String KEY_API_AUTH_CAD = "KEY_API_AUTH_CAD";
    private static final long KEY_API_AUTH_CAD_OFFSET = 4 * 60 * 40 * 1000;
    Context ctx;
    private SharedPreferences prefs;

    public Session(Context ctx) {
        this.ctx = ctx.getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    public void start(String token, String displayName, String email) {
        prefs.edit()
                .putString(KEY_API_AUTH, token)
                .putString(KEY_NAME, displayName)
                .putString(KEY_EMAIL, email)
                .putLong(KEY_API_AUTH_CAD, new Date().getTime() + KEY_API_AUTH_CAD_OFFSET)
                .commit();
    }

    public String getApiAuth() {
        return getValue(KEY_API_AUTH);
    }

    public String getName() {
        return getValue(KEY_NAME);
    }

    public String getEmail() {
        return getValue(KEY_EMAIL);
    }

    private String getValue(String key) {
        if (!isValidSession()) return null;
        return prefs.getString(key, null);
    }

    public boolean isValidSession() {
        return prefs.getLong(KEY_API_AUTH_CAD, 0) > new Date().getTime();
    }


}
