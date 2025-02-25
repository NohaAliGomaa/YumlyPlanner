package com.example.yumlyplanner.profilefragment.presenter;

import android.content.Context;

import com.example.yumlyplanner.loginfragment.view.LoginView;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.authentication.AuthenticationModelImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.network.LoginCallBack;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.remot.MealsRemotDataSourceImpl;
import com.example.yumlyplanner.profilefragment.view.ProfileView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter {
    private final ProfileView view;
    private final AuthenticationModelImpl model;
    private MealRepositry repositry;

    public ProfilePresenterImpl(ProfileView view, MealLocalDataSource mealLocalDataSource,Context context) {
        this.view = view;
        this.model = new AuthenticationModelImpl(mealLocalDataSource,context);
        repositry =MealRepositry.getInstance(mealLocalDataSource);
    }
    @Override
    public void logout(Context context) {
        model.logout(context);
        view.logoutUser();
    }

    @Override
    public void getAllMeals() {
        repositry.getAllMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals ->  model.backup(meals),
                        throwable -> {
                            throwable.printStackTrace(); // Log the error for debugging
                            view.showError("Failed to load planned meals.");
                        }
                );
    }

    @Override
    public String getName() {
        return  model.getName();
    }

    @Override
    public String getEmail() {
        return model.getEmail();
    }
}

