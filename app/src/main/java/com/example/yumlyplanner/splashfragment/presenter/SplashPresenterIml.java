package com.example.yumlyplanner.splashfragment.presenter;

import android.content.Context;

import com.example.yumlyplanner.loginfragment.view.LoginView;
import com.example.yumlyplanner.model.authentication.AuthenticationModelImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.splashfragment.view.SplashView;

public class SplashPresenterIml  implements SplashPresenter{
    private SplashView view;
    private AuthenticationModelImpl model;
    public SplashPresenterIml(SplashView view, MealLocalDataSource localDataSource, Context context) {
        this.view = view;
        this.model = new AuthenticationModelImpl(localDataSource, context);
    }
    @Override
    public void isLogged() {
            if (model.checkLoginStatus()) {
                view.navagiteToHome();
            } else {
                view.checkLoginStatus();
            }

    }
}
