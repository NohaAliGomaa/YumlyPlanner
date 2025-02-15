package com.example.yumlyplanner.model;

public interface AuthenticationModel {
    public void loginWithEmail(String email, String password, FireBaseCallBack callback);
    public void loginWithGoogle(String idToken, FireBaseCallBack callback);
}
