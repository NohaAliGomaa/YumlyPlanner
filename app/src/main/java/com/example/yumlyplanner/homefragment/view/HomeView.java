package com.example.yumlyplanner.homefragment.view;

import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public interface HomeView {
    public void showRandomMeal(Meal meal);
    public void showIngredient(List<Ingredient> ingredients );
    public  void  showCategory(List<Category> categories);
    public void sendMealCountryToDisplay(List<Meal> meals);
    public void sendMealIngredientsToDisplay(List<Meal> meals);
    public void sendMealCategoryToDisplay(List<Meal> meals);
    public void showError(String message);

}
