package com.example.yumlyplanner.registerfragment.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;

public interface RegisterPresenter {
    public void registerUser(String email, String password);
    public void signInWithGoogle(AuthCredential credential);

}
