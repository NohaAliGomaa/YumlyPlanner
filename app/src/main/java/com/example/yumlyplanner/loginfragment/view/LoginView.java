package com.example.yumlyplanner.loginfragment.view;

public interface LoginView {
    void showLoginSuccess();
    void showLoginError(String message);
    void navigateToHome();
    void showGoogleSignInError(String message);
    void showGoogleSignSuccess(String message);
}
