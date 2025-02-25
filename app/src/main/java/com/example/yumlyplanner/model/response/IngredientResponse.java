package com.example.yumlyplanner.model.response;

import com.example.yumlyplanner.model.pojo.Ingredient;

import java.util.ArrayList;

public class IngredientResponse {
    private ArrayList<Ingredient> meals;

    public ArrayList<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }
}
