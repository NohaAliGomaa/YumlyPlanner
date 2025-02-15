package com.example.yumlyplanner.loginfragment.presenter;

import android.content.Intent;

public interface LoginPresenter {

    void handleEmailLogin(String email, String password);
    void handleGoogleLogin(String idToken);
}
