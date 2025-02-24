package com.example.yumlyplanner.calendarfragmeng.presenter;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface CalendarPresenter {
    public void updateMealDate(String idMeal, String newDate);
    public void delete(Meal meal);
    public void getMealsByDate(String selectedDate);
    public void getAllPlanedMeal();

}
