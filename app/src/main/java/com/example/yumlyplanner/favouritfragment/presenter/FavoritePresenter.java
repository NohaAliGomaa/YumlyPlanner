package com.example.yumlyplanner.favouritfragment.presenter;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface FavoritePresenter {
    public void deletMealFromFavourit(String idMeal);
    public void  getAllFavouritMeal();
}
