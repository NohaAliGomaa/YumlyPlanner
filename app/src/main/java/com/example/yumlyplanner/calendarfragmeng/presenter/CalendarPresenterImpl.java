package com.example.yumlyplanner.calendarfragmeng.presenter;

import android.util.Log;

import com.example.yumlyplanner.calendarfragmeng.view.CalendarView;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.singlemeal.view.SingleMealView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenterImpl implements CalendarPresenter {
    private CalendarView view;
    private MealRepositry repositry;
    private static final String TAG = "calendarPresenterImpl";
    public CalendarPresenterImpl(CalendarView view, MealLocalDataSource mealLocalDataSource) {
        this.view = view;
        this.repositry = MealRepositry.getInstance(mealLocalDataSource);
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
    public void getMealsByDate(String selectedDate) {
        Log.i(TAG, "getMealsByDate: the date is"+selectedDate);
        repositry.getMealsByDate(selectedDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            Log.d(TAG, "Meals received: " + meals.size());
                            if (!meals.isEmpty()) {
                                Log.d(TAG, "First meal: " + meals.get(0));
                            }
                            view.onMealsLoaded(meals);
                        },
                        throwable -> {
                            Log.e(TAG, "Error fetching meals", throwable);
                            throwable.printStackTrace();
                            view.showError("Failed to load meals for the selected date.");
                        }
                );
    }

    public void getAllPlanedMeal() {
        repositry.getAllPlanedMeal()
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
