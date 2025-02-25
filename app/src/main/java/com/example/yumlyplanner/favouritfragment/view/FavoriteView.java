package com.example.yumlyplanner.favouritfragment.view;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public interface FavoriteView {
    public  void showError(String message);
    public  void onSuccess(String message);
    void onMealsLoaded(List<Meal> meals);
}
