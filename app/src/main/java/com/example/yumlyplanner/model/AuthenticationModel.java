package com.example.yumlyplanner.model;

public interface AuthenticationModel {
    public void loginWithEmail(String email, String password, LoginCallBack callback);
    public void loginWithGoogle(String idToken, LoginCallBack callback);
    public void authenticateWithFirebase(String idToken, RegisterCallBack callback);
}
