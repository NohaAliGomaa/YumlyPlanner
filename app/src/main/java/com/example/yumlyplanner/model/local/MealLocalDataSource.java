package com.example.yumlyplanner.model.local;

import android.content.Context;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSource {
    private MealDao dao;
    private Flowable<List<Meal>> data;

    private static MealLocalDataSource mealLocalDataSource = null;
    private MealLocalDataSource(Context context) {

        AppDataBase appDatabase = AppDataBase.getInstance(context.getApplicationContext());
        dao = appDatabase.mealDAO();
        data = dao.getAllFavouritMeal();

    }

    public static MealLocalDataSource getInstance(Context context) {

        if (mealLocalDataSource == null) {
           mealLocalDataSource = new MealLocalDataSource(context);
        }
        return mealLocalDataSource;
    }

    public Flowable<List<Meal>> getAllfavouritMeal() {
        return data;
    }
    public Flowable<List<Meal>> getAllPlanedMeal()
    {
        return dao.getAllPlanedMeal();
    }
    public void insert(Meal meal) {
        new Thread(() -> {
            dao.insertAll(meal);
        }).start();
    }

    public void delete(Meal meal) {
        new Thread(() -> {
            dao.delete(meal);
        }).start();
    }
    public Meal getMealById(String mealId)
    {
        return  dao.getMealById(mealId);
    }
    public Completable updateMealDate(int idMeal, String newDate)
    {
        return  dao.updateMealDate(idMeal,newDate);
    }
}
