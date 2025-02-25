package com.example.yumlyplanner.loginfragment.presenter;

import android.content.Context;

import com.example.yumlyplanner.loginfragment.view.LoginView;
import com.example.yumlyplanner.model.authentication.AuthenticationModelImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.network.LoginCallBack;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginPresenter{
    private final LoginView view;
    private final AuthenticationModelImpl model;

    public LoginPresenterImpl(LoginView view, MealLocalDataSource localDataSource, Context context) {
        this.view = view;
        this.model = new AuthenticationModelImpl(localDataSource,context);
    }

    @Override
    public void handleEmailLogin(String email, String password) {
        model.loginWithEmail(email, password, new LoginCallBack() {
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
        model.loginWithGoogle(idToken, new LoginCallBack()  {
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

    @Override
    public void loginAsGuest(Context context, LoginCallBack callback) {
        model.loginAsGuest(context,callback);
    }


}
