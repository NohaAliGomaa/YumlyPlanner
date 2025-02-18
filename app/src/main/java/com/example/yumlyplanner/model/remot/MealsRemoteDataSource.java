package com.example.yumlyplanner.model.remot;

import android.database.Observable;

import com.example.yumlyplanner.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsRemoteDataSource {
    @GET( "random.php")
    Call<MealResponse> getRandomMeal();
}
