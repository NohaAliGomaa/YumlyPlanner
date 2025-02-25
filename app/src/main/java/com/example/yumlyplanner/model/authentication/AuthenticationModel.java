package com.example.yumlyplanner.model.authentication;

import android.content.Context;

import com.example.yumlyplanner.model.network.LoginCallBack;
import com.example.yumlyplanner.model.network.RegisterCallBack;
import com.example.yumlyplanner.model.pojo.Meal;
import com.google.firebase.auth.AuthCredential;

import java.util.List;

public interface AuthenticationModel {
    public void loginWithEmail(String email, String password, LoginCallBack callback);
    public void loginWithGoogle(String idToken, LoginCallBack callback);
    public void authenticateWithFirebase(String idToken, RegisterCallBack callback);
    public void registerUser(String email, String password, RegisterCallBack listener);
    public void signInWithGoogle(AuthCredential credential, RegisterCallBack listener);
    public  void logout(Context context);
    public  void backup(List<Meal> mealList);
    public  void restorData(String userId);
}
