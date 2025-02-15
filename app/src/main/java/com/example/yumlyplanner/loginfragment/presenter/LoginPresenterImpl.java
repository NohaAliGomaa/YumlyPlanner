package com.example.yumlyplanner.loginfragment.presenter;

import com.example.yumlyplanner.loginfragment.view.LoginViewInterface;
import com.example.yumlyplanner.model.AuthenticationModel;
import com.example.yumlyplanner.model.AuthenticationModelImpl;
import com.example.yumlyplanner.model.FireBaseCallBack;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginPresenter{
    private final LoginViewInterface view;
    private final AuthenticationModelImpl model;

    public LoginPresenterImpl(LoginViewInterface view) {
        this.view = view;
        this.model = new AuthenticationModelImpl();
    }

    @Override
    public void handleEmailLogin(String email, String password) {
        model.loginWithEmail(email, password, new FireBaseCallBack() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.showLoginSuccess( );
                view.navigateToHome();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showLoginError(errorMessage);
            }
        });
    }

    @Override
    public void handleGoogleLogin(String idToken) {
        model.loginWithGoogle(idToken, new FireBaseCallBack()  {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.showGoogleSignSuccess(user.getEmail());
                view.navigateToHome();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showGoogleSignInError(errorMessage);
            }
        });

    }
}
