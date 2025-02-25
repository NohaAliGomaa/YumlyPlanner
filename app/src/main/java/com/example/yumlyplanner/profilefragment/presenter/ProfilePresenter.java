package com.example.yumlyplanner.profilefragment.presenter;

import android.content.Context;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ProfilePresenter {
    public void logout(Context context);
    public void getAllMeals();
    public String getName() ;
    public String getEmail() ;

}
