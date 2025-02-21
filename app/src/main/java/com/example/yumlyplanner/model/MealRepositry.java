package com.example.yumlyplanner.model;

import com.example.yumlyplanner.model.remot.MealsRemotDataSourceImpl;
import com.example.yumlyplanner.model.response.CategoryResponse;
import com.example.yumlyplanner.model.response.IngredientResponse;
import com.example.yumlyplanner.model.response.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MealRepositry {
    private MealsRemotDataSourceImpl remotDataSource;
    private static MealRepositry mealRepositry=null;
    private  MealRepositry()
    {
        remotDataSource=MealsRemotDataSourceImpl.getInstance();
    }

    public static MealRepositry getInstance() {
        if(mealRepositry == null)
        {
            mealRepositry=new MealRepositry();
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
}
