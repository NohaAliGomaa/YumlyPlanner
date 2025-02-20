package com.example.yumlyplanner.homefragment.presenter;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.yumlyplanner.homefragment.view.HomeView;
import com.example.yumlyplanner.model.IngredientResponse;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.NetworkCallBack;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter, NetworkCallBack {
    private HomeView view;
    private  MealRepositry repositry;
    private static final String TAG = "HomePresenterImpl";

    public HomePresenterImpl(HomeView _view) {
        this.view=_view;
        repositry=MealRepositry.getInstance();
    }

    @Override
    public void getRandomMeal() {
       repositry.getRandomMeal()
                .subscribeOn(Schedulers.io())  // Perform request on IO thread
                .observeOn(AndroidSchedulers.mainThread())  // Observe result on Main thread
                .subscribe(
                        mealResponse -> view.showRandomMeal(mealResponse.getMeals().get(0)), // Pass meal to View
                        throwable -> view.showError(throwable.getMessage()) // Handle error
                );
    }

    @Override
    public void getIngredient() {
      repositry.getIngredient() .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ingredientResponse -> ingredientResponse.getMeals())
                .subscribe(
                        mealResponse -> view.showIngredient(mealResponse),
                        throwable -> view.showError(throwable.getMessage())
    );
    }

    @Override
    public void getAllCategories() {
        repositry.getAllCategories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .map(categoryResponse-> categoryResponse.getCategories())
                .subscribe(
                        categories -> view.showCategory(categories.getCategories()),
                        throwable -> view.showError(throwable.getMessage())
                );
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        if (meals != null && !meals.isEmpty() && view != null && ((Fragment) view).isAdded()) {
            view.showRandomMeal(meals.get(0));
        } else {
            Log.e(TAG, "onSuccessResult: Received an empty meal list");

        }
    }

    @Override
    public void onFailurResult(String erroMsg) {
        Log.i(TAG, "onFailurResult: "+erroMsg);
    }
}
