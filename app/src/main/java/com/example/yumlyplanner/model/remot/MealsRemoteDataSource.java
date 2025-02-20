package com.example.yumlyplanner.model.remot;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.yumlyplanner.model.CategoryResponse;
import com.example.yumlyplanner.model.IngredientResponse;
import com.example.yumlyplanner.model.MealResponse;

public interface MealsRemoteDataSource {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

//    @GET("filter.php")
//    Single<MealResponse> getFilteredMealsCountries(@Query("a") String country);
//
//    @GET("filter.php")
//    Single<MealResponse> getFilteredMealsCategories(@Query("c") String category);
//
//    @GET("filter.php")
//    Single<MealResponse> getFilteredMealsIngredients(@Query("i") String ingredient);
//
//    @GET("lookup.php")
//    Single<MealResponse> getDetailedMeal(@Query("i") String detailedMeal);

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredient();

    @GET("categories.php")
    Single<CategoryResponse> getAllCategories();
}
