package com.example.yumlyplanner.searchfragment.presenter;

import com.example.yumlyplanner.model.response.MealResponse;

import io.reactivex.rxjava3.core.Single;

public interface SearchPresenter {
    public void getIngredient();
    public  void getAllCategories();
    public void getMealbyCategory(String categoryName);
    public void getMealbycIngredient(String ingredientName);
    public void getMealbyCountry(String countryName);

    public void getMealListWithLetter( char letter);

}
