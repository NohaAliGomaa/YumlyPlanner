package com.example.yumlyplanner.model;

import com.google.firebase.auth.FirebaseUser;

public interface FireBaseCallBack {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);
}
