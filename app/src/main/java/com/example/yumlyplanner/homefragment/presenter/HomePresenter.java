package com.example.yumlyplanner.homefragment.presenter;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public interface HomePresenter {
    public void getRandomMeal();
    public void getIngredient();
    public  void getAllCategories();
    public void getMealbyCategory(String categoryName);
    public void getMealbycIngredient(String ingredientName);
    public void getMealbyCountry(String countryName);



}
