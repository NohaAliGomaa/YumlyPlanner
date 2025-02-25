package com.example.yumlyplanner.model.network;

import com.google.firebase.auth.FirebaseUser;

public interface LoginCallBack {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);

}
