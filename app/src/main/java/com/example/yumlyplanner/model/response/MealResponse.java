package com.example.yumlyplanner.model.response;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }


}

