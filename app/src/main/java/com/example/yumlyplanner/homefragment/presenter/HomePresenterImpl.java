package com.example.yumlyplanner.homefragment.presenter;

import android.util.Log;
import androidx.fragment.app.Fragment;
import com.example.yumlyplanner.homefragment.view.HomeView;
import com.example.yumlyplanner.homefragment.view.OnHomeRecycleClick;
import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.authentication.AuthenticationModelImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.network.NetworkCallBack;
import com.example.yumlyplanner.model.pojo.Meal;
import java.lang.ref.WeakReference;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter ,NetworkCallBack{
    private HomeView view;
    private MealRepositry repository;
    private OnHomeRecycleClick onHomeRecycleClick;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AuthenticationModelImpl model;
    private static final String TAG = "HomePresenterImpl";

    public HomePresenterImpl(HomeView view, OnHomeRecycleClick onHomeRecycleClick, MealLocalDataSource mealLocalDataSource) {
        this.view = view;
        this.onHomeRecycleClick = onHomeRecycleClick;
        this.repository = MealRepositry.getInstance(mealLocalDataSource);

    }
    @Override
    public void getRandomMeal() {
        Disposable disposable = repository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealResponse -> {
                            if (view != null && ((Fragment) view).isAdded()) {
                                view.showRandomMeal(mealResponse.getMeals().get(0));
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.showError(throwable.getMessage());
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }
    @Override
    public void getIngredient() {
        compositeDisposable.add(repository.getIngredient()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ingredientResponse -> ingredientResponse.getMeals())
                .subscribe(
                        meals -> {

                            if (view != null) {
                                view.showIngredient(meals);
                            }
                        },
                        throwable -> handleError(throwable)
                ));
    }
    @Override
    public void getAllCategories() {
        compositeDisposable.add(repository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {

                            if (view != null) {
                                view.showCategory(categories.getCategories());
                            }
                        },
                        throwable -> handleError(throwable)
                ));
    }
    @Override
    public void getMealbyCategory(String categoryName) {
        compositeDisposable.add(repository.getFilteredMealsCategories(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> {

                            if (view != null) {
                                view.sendMealCategoryToDisplay(meals);
                            }
                        },
                        throwable -> handleError(throwable)
                ));
    }
    @Override
    public void getMealbycIngredient(String ingredientName) {
        compositeDisposable.add(repository.getFilteredMealsIngredients(ingredientName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> {

                            if (view != null) {
                                view.sendMealIngredientsToDisplay(meals);
                            }
                        },
                        throwable -> handleError(throwable)
                ));
    }
    @Override
    public void getMealbyCountry(String countryName) {
        compositeDisposable.add(repository.getFilteredMealsCountries(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> {

                            if (view != null) {
                                view.sendMealCountryToDisplay(meals);
                            }
                        },
                        throwable -> handleError(throwable)
                ));
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

    private void handleError(Throwable throwable) {

        if (view != null) {
            view.showError("Something went wrong. Please try again.");
        }
    }
    public void clear() {
        compositeDisposable.clear(); // Clears all ongoing network requests
    }

}
