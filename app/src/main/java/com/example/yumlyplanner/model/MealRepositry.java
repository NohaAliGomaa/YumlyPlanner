package com.example.yumlyplanner.model;

import android.util.Log;

import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.remot.MealsRemotDataSourceImpl;
import com.example.yumlyplanner.model.response.CategoryResponse;
import com.example.yumlyplanner.model.response.IngredientResponse;
import com.example.yumlyplanner.model.response.MealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealRepositry {
    private MealsRemotDataSourceImpl remotDataSource;
    private MealLocalDataSource localDataSource;
    private static MealRepositry mealRepositry=null;
    private static final String TAG = "MealRepositry";
    private  MealRepositry(MealLocalDataSource mealLocalDataSource)
    {
        remotDataSource=MealsRemotDataSourceImpl.getInstance();
        this.localDataSource=mealLocalDataSource;
    }

    public static MealRepositry getInstance(MealLocalDataSource mealLocalDataSource) {
        if(mealRepositry == null)
        {
            mealRepositry=new MealRepositry( mealLocalDataSource);
        }
        return mealRepositry;
    }
    public Single<MealResponse> getRandomMeal()
    {
      return  remotDataSource.getRandomMeal();
    }
    public  Single<IngredientResponse> getIngredient(){
        return  remotDataSource.getIngredient();
    }
    public  Single<CategoryResponse> getAllCategories()
    {
        return  remotDataSource.getAllCategories();
    }

    public Single<MealResponse> getFilteredMealsCountries( String country)
    {
        return  remotDataSource.getFilteredMealsCountries(country);
    }

    public Single<MealResponse> getFilteredMealsCategories( String category)
    {
        return remotDataSource.getFilteredMealsCategories(category);
    }

    public Single<MealResponse> getFilteredMealsIngredients( String ingredient)
     {
         return remotDataSource.getFilteredMealsIngredients(ingredient);
     }
    public Single<MealResponse> getMealListWithLetter( char letter)
    {
        return  remotDataSource.getMealListWithLetter(letter);
    }
    public Single<MealResponse> getDetailedMeal( String mealId)
    {
        Log.i(TAG, "getDetailedMeal: the meal in progress"+mealId);
        return  remotDataSource.getDetailedMeal(mealId);
    }
    public Flowable<List<Meal>> getAllFavouritMeal() {
        return localDataSource.getAllfavouritMeal();
    }
    public Flowable<List<Meal>> getAllPlanedMeal()
    {
        return localDataSource.getAllPlanedMeal();
    }
    public Completable insert(Meal  meal) {

       return localDataSource.insert(meal);

    }

    public Completable delete(Meal meal) {

      return   localDataSource.delete(meal);

    }
    public  Completable deletMealFromFavourit(String idMeal)
    {
        Log.i(TAG, "deletMealFromFavourit: "+idMeal);
        return  localDataSource.deletMealFromFavourit(idMeal);
    }
    public Flowable<Meal> getMealById(String mealId)
    {
        return  localDataSource.getMealById(mealId);
    }
    public Completable updateMealDate(String idMeal, String newDate)
    {
        return  localDataSource.updateMealDate(idMeal,newDate);
    }
    public Flowable<List<Meal>> getMealsByDate(String selectedDate)
    {
        Log.i(TAG, "repo getMealsByDate: the date is"+selectedDate);
        return localDataSource.getMealsByDate(selectedDate);
    }
    public Single<List<Meal>>  getAllMeals()
    {
        return  localDataSource.getAllMeals();
    }
    public  void insertMeals(List<Meal> meals)
    {
       localDataSource.insertMeals(meals);
    }

}
