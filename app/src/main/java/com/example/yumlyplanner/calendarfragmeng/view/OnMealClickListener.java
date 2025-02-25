package com.example.yumlyplanner.calendarfragmeng.view;

import com.example.yumlyplanner.model.pojo.Meal;

public interface OnMealClickListener {
    void onMealClick(Meal meal);
    void onDeleteMeal(Meal meal);
}