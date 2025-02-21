package com.example.yumlyplanner.registerfragment.presenter;

import com.example.yumlyplanner.registerfragment.view.RegisterView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView registerView;
    private FirebaseAuth firebaseAuth;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void registerUser(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            registerView.showError("Email and password cannot be empty");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registerView.showSuccess("Registration Successful!");
                        registerView.navigateToHome();
                    } else {
                        registerView.showError("Registration Failed: " + task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signInWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registerView.showSuccess("Google Sign-In Successful!");
                        registerView.navigateToHome();
                    } else {
                        registerView.showError("Google Sign-In Failed: " + task.getException().getMessage());
                    }
                });
    }
}
