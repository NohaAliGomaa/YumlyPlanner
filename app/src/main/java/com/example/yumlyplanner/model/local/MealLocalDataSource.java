package com.example.yumlyplanner.model.local;

import android.content.Context;
import android.util.Log;

import androidx.room.Query;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSource {
    private MealDao dao;
    private Flowable<List<Meal>> data;
    private static final String TAG = "MealLocalDataSource";
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
    public Completable insert(Meal meal) {
         return    dao.insertAll(meal);

    }

    public Completable delete(Meal meal) {

         return   dao.deleteMealById(meal);
    }

    public  Completable deletMealFromFavourit(String idMeal)
    {  Log.i(TAG, " in LocalDataSourcedeletMealFromFavourit: "+idMeal);
        return  dao.updateMealFavouriteStatus(idMeal);
    }
    public Flowable<Meal> getMealById(String mealId)
    {
        return  dao.getMealById(mealId);
    }
    public Completable updateMealDate(String idMeal, String newDate)
    {
        return  dao.updateMealDate(idMeal,newDate);
    }
    public Flowable<List<Meal>> getMealsByDate(String selectedDate)
    {  Log.i(TAG, "localDS getMealsByDate: the date is"+selectedDate);

        return  dao.getMealsByDate(selectedDate);
    }
}
