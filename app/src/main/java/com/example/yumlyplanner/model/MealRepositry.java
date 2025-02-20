package com.example.yumlyplanner.model;

import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.remot.MealsRemotDataSourceImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

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
}
