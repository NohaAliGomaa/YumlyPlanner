package com.example.yumlyplanner.registerfragment.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public interface RegisterPresenter {
    public void handleGoogleSignUpResult(Task<GoogleSignInAccount> task);

}
