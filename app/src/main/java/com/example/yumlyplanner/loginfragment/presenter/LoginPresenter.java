package com.example.yumlyplanner.loginfragment.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yumlyplanner.model.authentication.SessionManager;
import com.example.yumlyplanner.model.network.LoginCallBack;

public interface LoginPresenter {

    void handleEmailLogin(String email, String password);
    void handleGoogleLogin(String idToken);
    public void loginAsGuest(Context context, LoginCallBack callback);
}
