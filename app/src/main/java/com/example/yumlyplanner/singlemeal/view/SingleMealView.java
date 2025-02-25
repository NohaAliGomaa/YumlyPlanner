package com.example.yumlyplanner.singlemeal.view;

import com.example.yumlyplanner.model.pojo.Meal;

public interface SingleMealView {
    public  void displayMealInScreen(Meal meal);
    public  void showError(String message);
    public  void onSuccess(String message);
}
