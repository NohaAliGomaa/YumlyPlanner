package com.example.yumlyplanner.model;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthenticationModelImpl implements AuthenticationModel{
    private final FirebaseAuth mAuth;

    public AuthenticationModelImpl() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void loginWithEmail(String email, String password, FireBaseCallBack callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            callback.onSuccess(user);
                        } else {
                            callback.onFailure("Please verify your email!");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void loginWithGoogle(String idToken, FireBaseCallBack callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }
}
