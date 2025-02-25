package com.example.yumlyplanner.model.remot;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.yumlyplanner.model.response.CategoryResponse;
import com.example.yumlyplanner.model.response.IngredientResponse;
import com.example.yumlyplanner.model.response.MealResponse;

public interface MealsRemoteDataSource {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("filter.php")
    Single<MealResponse> getFilteredMealsCountries(@Query("a") String country);

    @GET("filter.php")
    Single<MealResponse> getFilteredMealsCategories(@Query("c") String category);

    @GET("filter.php")
    Single<MealResponse> getFilteredMealsIngredients(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<MealResponse> getDetailedMeal(@Query("i") String mealId);
    @GET("search.php")
    Single<MealResponse> getMealListWithLetter(@Query("f") char letter);

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredient();

    @GET("categories.php")
    Single<CategoryResponse> getAllCategories();
}
