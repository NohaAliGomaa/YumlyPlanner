package com.example.yumlyplanner.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.response.MealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDao {

    @Query("SELECT * From meals")
    Observable<List<MealResponse>> getAllMeals();

    @Query("SELECT * FROM meals WHERE id_meal = :idMeal")
    Meal getMealById(String idMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Meal meal);

    @Delete
    void delete(Meal meal);

}
