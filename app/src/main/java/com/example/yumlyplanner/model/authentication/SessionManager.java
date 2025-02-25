package com.example.yumlyplanner.model.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_GUEST_MODE = "guest_mode";
    private static final String KEY_USER_UID = "user_uid";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";

    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    // Manage guest mode
    public void setGuestMode(boolean isGuest) {
        sharedPreferences.edit().putBoolean(KEY_GUEST_MODE, isGuest).apply();
    }

    public boolean isGuestMode() {
        return sharedPreferences.getBoolean(KEY_GUEST_MODE, false);
    }

    // Save user login session
    public void saveUserSession(String uid, String name, String email) {
        sharedPreferences.edit()
                .putString(KEY_USER_UID, uid)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_EMAIL, email)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public String getUserUID() {
        return sharedPreferences.getString(KEY_USER_UID, null);
    }

    public boolean isLoggedIn() {
        boolean loggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        Log.i("SessionManager", "Checking login status: " + loggedIn);
        return loggedIn;
    }

    public void logout() {
        sharedPreferences.edit()
                .remove(KEY_GUEST_MODE)
                .remove(KEY_USER_UID)
                .remove(KEY_IS_LOGGED_IN)
                .apply();
        instance = null;
    }
    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "Guest");
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

}
