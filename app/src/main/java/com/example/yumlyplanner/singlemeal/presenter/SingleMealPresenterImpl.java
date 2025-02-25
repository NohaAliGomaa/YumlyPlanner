package com.example.yumlyplanner.singlemeal.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.yumlyplanner.homefragment.view.HomeView;
import com.example.yumlyplanner.homefragment.view.OnHomeRecycleClick;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.response.MealResponse;
import com.example.yumlyplanner.singlemeal.view.SingleMealView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SingleMealPresenterImpl  implements SingleMealPresenter{
    private SingleMealView view;
    private MealRepositry repositry;
    private static final String TAG = "SingleMealPresenterImpl";
    public SingleMealPresenterImpl(SingleMealView view, MealLocalDataSource mealLocalDataSource) {
        this.view = view;
        this.repositry = MealRepositry.getInstance(mealLocalDataSource);
    }

    @Override
    public void addtoFavourit(Meal meal) {
        repositry.insert(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onSuccess("the Meal is Set to be Favourit"),
                        throwable -> handleError(throwable)
                );
    }

    @Override
    public void updateMealDate(String idMeal, String newDate) {
        repositry.updateMealDate(idMeal,newDate).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> view.onSuccess("the Date is Set to "+newDate),
                        throwable -> handleError(throwable)
                );
    }

    @Override
    public void getDetailedMeal(String mealId) {
        repositry.getDetailedMeal(mealId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> {
                                   view.displayMealInScreen(meals.get(0));
                                   Log.i(TAG, "getDetailedMeal: meal is"+meals.get(0).getStrMeal());
                        },
                        throwable -> handleError(throwable)
                );


    }
    public  void deletMealFromFavourit(String idMeal)
    {
        repositry.deletMealFromFavourit(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onSuccess("Meal removed from favorites"+idMeal),
                        throwable -> handleError(throwable)
                );
    }
    private void handleError(Throwable throwable) {

        if (view != null) {
            view.showError("Something went wrong. Please try again.");
        }
    }
    public void delete(Meal meal)
    {  Log.d(TAG, "Attempting to delete meal with ID: " + meal.getMealId());
        repositry.delete(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onSuccess("Meal deleted successfully"),
                        throwable -> handleError(throwable)
                );
    }

}
