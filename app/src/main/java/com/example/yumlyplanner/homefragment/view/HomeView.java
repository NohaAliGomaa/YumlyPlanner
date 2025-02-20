package com.example.yumlyplanner.homefragment.view;

import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public interface HomeView {
    public void showRandomMeal(Meal meal);
    public void showIngredient(List<Ingredient> ingredients );
    public  void  showCategory(List<Category> categories);

    void showError(String message);
}
