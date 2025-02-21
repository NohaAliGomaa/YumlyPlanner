package com.example.yumlyplanner.searchfragment.view;

import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public interface SearchView {
    public void showIngredient(List<Ingredient> ingredients );
    public  void  showCategory(List<Category> categories);
    public void sendMealCountryToDisplay(List<Meal> meals);
    public void sendMealIngredientsToDisplay(List<Meal> meals);
    public void sendMealCategoryToDisplay(List<Meal> meals);

    public  void  displayMealListWithLetter(List<Meal> meals);
    void showError(String message);
}
