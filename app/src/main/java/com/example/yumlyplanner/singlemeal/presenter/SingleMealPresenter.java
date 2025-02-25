package com.example.yumlyplanner.singlemeal.presenter;

import com.example.yumlyplanner.model.local.MealDao;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.response.MealResponse;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface SingleMealPresenter {
    public  void addtoFavourit(Meal meal);//use insert
    public void updateMealDate(String idMeal, String newDate);
    public void  getDetailedMeal(String mealId);
    public  void deletMealFromFavourit(String idMeal);
    public void delete(Meal meal);

}
