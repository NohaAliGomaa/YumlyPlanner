package com.example.yumlyplanner.registerfragment.presenter;

import com.example.yumlyplanner.model.AuthenticationModel;
import com.example.yumlyplanner.model.AuthenticationModelImpl;
import com.example.yumlyplanner.model.RegisterCallBack;
import com.example.yumlyplanner.registerfragment.view.RegisterView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView view;
    private AuthenticationModelImpl model;

    public RegisterPresenterImpl(RegisterView view) {
        this.view = view;
        this.model = new AuthenticationModelImpl();
    }

    @Override
    public void handleGoogleSignUpResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            model.authenticateWithFirebase(account.getIdToken(), new RegisterCallBack() {
                @Override
                public void showOnRegisterSuccess(String successMessage) {
                    view.showSuccess(successMessage);
                    view.navigateToHome();
                }

                @Override
                public void showOnRegisterError(String errorMessage) {
                    view.showError(errorMessage);
                }
            });
        } catch (ApiException e) {
            view.showError("Google Sign-Up failed: " + e.getMessage());
        }
    }
}
