package com.example.yumlyplanner.model;

import android.database.Observable;

import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET( "random.php")
    Observable<MealResponse> getRandomMeal();
}
