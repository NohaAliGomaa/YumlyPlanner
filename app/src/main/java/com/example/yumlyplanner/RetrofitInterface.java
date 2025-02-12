package com.example.yumlyplanner;

import android.database.Observable;

import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET( "random.php")
    Observable<MealResponse> getRandomMeal();
}
