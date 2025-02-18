package com.example.yumlyplanner.homefragment.presenter;

import android.util.Log;

import com.example.yumlyplanner.homefragment.view.HomeView;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.NetworkCallBack;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, NetworkCallBack {
    private HomeView view;
    private  MealRepositry repositry;
    private static final String TAG = "HomePresenterImpl";

    public HomePresenterImpl(HomeView _view) {
        this.view=_view;
        repositry=MealRepositry.getInstance();
    }

    @Override
    public void getRandomMeal() {
       repositry.getRandomMeal(this);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            view.showRandomMeal(meals.get(0));
        } else {
            Log.e(TAG, "onSuccessResult: Received an empty meal list");

        }
    }

    @Override
    public void onFailurResult(String erroMsg) {
        Log.i(TAG, "onFailurResult: "+erroMsg);
    }
}
