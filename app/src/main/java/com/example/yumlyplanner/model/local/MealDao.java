package com.example.yumlyplanner.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {

    @Query("SELECT * From meals WHERE  Favourite = true")
    Flowable<List<Meal>> getAllFavouritMeal();
    @Query("SELECT * From meals WHERE  date != Null")
    Flowable<List<Meal>> getAllPlanedMeal();

    @Query("SELECT * FROM meals WHERE id_meal = :idMeal ")
    Meal getMealById(String idMeal);
    @Query("UPDATE meals SET date = :newDate WHERE id_meal = :idMeal")
    Completable updateMealDate(int idMeal, String newDate);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Meal meal);
    @Delete
    void delete(Meal meal);

}
