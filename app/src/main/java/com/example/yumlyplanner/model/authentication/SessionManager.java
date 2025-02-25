package com.example.yumlyplanner.model.authentication;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_GUEST_MODE = "guest_mode";

    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void setGuestMode(boolean isGuest) {
        sharedPreferences.edit().putBoolean(KEY_GUEST_MODE, isGuest).apply();
    }

    public boolean isGuestMode() {
        return sharedPreferences.getBoolean(KEY_GUEST_MODE, false);
    }
}

