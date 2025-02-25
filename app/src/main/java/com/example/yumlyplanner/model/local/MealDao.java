package com.example.yumlyplanner.model.local;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.response.MealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {
        @Query("SELECT * FROM meals")
        Single<List<Meal>> getAllMeals();
        @Query("SELECT * FROM meals WHERE isFavourit = 1")
        Flowable<List<Meal>> getAllFavouritMeal();
        @Query("SELECT * FROM meals WHERE date = :selectedDate")
        Flowable<List<Meal>> getMealsByDate(String selectedDate);

        @Query("SELECT * FROM meals WHERE date IS NOT NULL")
        Flowable<List<Meal>> getAllPlanedMeal();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insertAll(Meal meal);
        @Delete
        Completable deleteMealById(Meal meal);

        @Query("UPDATE meals SET isFavourit = 0 WHERE id_meal = :idMeal")
        Completable updateMealFavouriteStatus(String idMeal);

        @Query("SELECT * FROM meals WHERE id_meal = :mealId")
        Flowable<Meal> getMealById(String mealId);

        @Query("UPDATE meals SET date = :newDate WHERE id_meal = :idMeal")
        Completable updateMealDate(String idMeal, @Nullable String newDate);
}