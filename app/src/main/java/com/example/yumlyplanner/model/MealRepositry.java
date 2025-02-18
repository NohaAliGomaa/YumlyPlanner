package com.example.yumlyplanner.model;

import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.remot.MealsRemotDataSourceImpl;

import java.util.List;

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
    public void getRandomMeal(NetworkCallBack networkCallBack)
    {

      remotDataSource.getRandomMeal(networkCallBack);

    }
}
