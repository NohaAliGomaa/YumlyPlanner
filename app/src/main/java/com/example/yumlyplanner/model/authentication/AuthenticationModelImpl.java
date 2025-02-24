package com.example.yumlyplanner.model.authentication;

import com.example.yumlyplanner.model.network.LoginCallBack;
import com.example.yumlyplanner.model.network.RegisterCallBack;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthenticationModelImpl implements AuthenticationModel {
    private final FirebaseAuth mAuth;

    public AuthenticationModelImpl() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void loginWithEmail(String email, String password, LoginCallBack callback) {
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
    public void loginWithGoogle(String idToken, LoginCallBack callback) {
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

    @Override
    public void authenticateWithFirebase(String idToken, RegisterCallBack callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                            callback.showOnRegisterSuccess("Registration successful!");
                        } else {
                            callback.showOnRegisterError("User already exists. Please log in instead.");
                        }
                    } else {
                        callback.showOnRegisterError("Registration failed: " + task.getException().getMessage());
                    }
                });
    }


    @Override
    public void registerUser(String email, String password, RegisterCallBack listener) {
       mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.showOnRegisterSuccess("the register is done");
                    } else {
                        listener.showOnRegisterError(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signInWithGoogle(AuthCredential credential, RegisterCallBack listener) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.showOnRegisterSuccess("register with google is done");
                    } else {
                        listener.showOnRegisterError(task.getException().getMessage());
                    }
                });

    }
    @Override
    public void logout() {
        mAuth.signOut();
    }
}
