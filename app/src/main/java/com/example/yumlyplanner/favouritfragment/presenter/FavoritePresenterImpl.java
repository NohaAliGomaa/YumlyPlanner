package com.example.yumlyplanner.favouritfragment.presenter;

import android.util.Log;

import com.example.yumlyplanner.calendarfragmeng.view.CalendarView;
import com.example.yumlyplanner.favouritfragment.view.FavoriteView;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Meal;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImpl implements FavoritePresenter{
    private FavoriteView view;
    private MealRepositry repositry;
    private static final String TAG = "calendarPresenterImpl";
    public FavoritePresenterImpl(FavoriteView view, MealLocalDataSource mealLocalDataSource) {
        this.view = view;
        this.repositry = MealRepositry.getInstance(mealLocalDataSource);
    }

    private void handleError(Throwable throwable) {

        if (view != null) {
            view.showError("Something went wrong. Please try again.");
        }
    }

    @Override
    public void deletMealFromFavourit(String idMeal) {
        repositry.deletMealFromFavourit(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onSuccess("the Meal has been Deleted"),
                        throwable -> {
                            throwable.printStackTrace(); // Log the error for debugging
                            view.showError("Failed to load planned meals.");
                        }
                );
    }

    @Override
    public void getAllFavouritMeal() {
        repositry.getAllFavouritMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> view.onMealsLoaded(meals),
                        throwable -> {
                            throwable.printStackTrace(); // Log the error for debugging
                            view.showError("Failed to load planned meals.");
                        }
                );
    }
}
