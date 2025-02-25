package com.example.yumlyplanner.registerfragment.presenter;

import android.content.Context;

import com.example.yumlyplanner.model.authentication.AuthenticationModelImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.network.LoginCallBack;
import com.example.yumlyplanner.model.network.RegisterCallBack;
import com.example.yumlyplanner.model.remot.MealsRemotDataSourceImpl;
import com.example.yumlyplanner.registerfragment.view.RegisterView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView registerView;
    private AuthenticationModelImpl firebaseAuth;

    public RegisterPresenterImpl(RegisterView registerView, MealLocalDataSource local, Context context) {
        this.registerView = registerView;
        this.firebaseAuth = new AuthenticationModelImpl(local,context);
    }

    @Override
    public void registerUser(String email, String password) {
      firebaseAuth.registerUser(email, password, new RegisterCallBack() {
          @Override
          public void showOnRegisterSuccess(String successMessage) {
              registerView.showSuccess(successMessage);
              registerView.navigateToHome();
          }

          @Override
          public void showOnRegisterError(String errorMessage) {
              registerView.showError(errorMessage);
          }
      });
    }

    @Override
    public void signInWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithGoogle(credential, new RegisterCallBack() {
            @Override
            public void showOnRegisterSuccess(String successMessage) {
                registerView.showSuccess(successMessage);
                registerView.navigateToHome();
            }

            @Override
            public void showOnRegisterError(String errorMessage) {
                registerView.showError(errorMessage);
            }
        });
    }
    @Override
    public void loginAsGuest(Context context, LoginCallBack callback) {
        firebaseAuth.loginAsGuest(context,callback);
    }

}
