package com.example.yumlyplanner.model;

import com.example.yumlyplanner.model.network.LoginCallBack;
import com.example.yumlyplanner.model.network.RegisterCallBack;
import com.google.firebase.auth.AuthCredential;

public interface AuthenticationModel {
    public void loginWithEmail(String email, String password, LoginCallBack callback);
    public void loginWithGoogle(String idToken, LoginCallBack callback);
    public void authenticateWithFirebase(String idToken, RegisterCallBack callback);
    public void registerUser(String email, String password, RegisterCallBack listener);
    public void signInWithGoogle(AuthCredential credential, RegisterCallBack listener);
}
