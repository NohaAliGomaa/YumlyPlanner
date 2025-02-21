package com.example.yumlyplanner.searchfragment.presenter;

import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.searchfragment.view.OnSearchRecycleClick;
import com.example.yumlyplanner.searchfragment.view.SearchView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private SearchView view;
    private final MealRepositry repository;
    private final OnSearchRecycleClick onSearchRecycleClick;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenterImpl(SearchView view, OnSearchRecycleClick onSearchRecycleClick) {
        this.view = view;
        this.onSearchRecycleClick = onSearchRecycleClick;
        this.repository = MealRepositry.getInstance();
    }

    @Override
    public void getIngredient() {
        if (view == null) return;

        compositeDisposable.add(repository.getIngredient()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ingredientResponse -> ingredientResponse.getMeals())
                .subscribe(
                        meals -> view.showIngredient(meals),
                        this::handleError
                ));
    }

    @Override
    public void getAllCategories() {
        if (view == null) return;

        compositeDisposable.add(repository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> view.showCategory(categories.getCategories()),
                        this::handleError
                ));
    }

    @Override
    public void getMealbyCategory(String categoryName) {
        if (view == null) return;

        compositeDisposable.add(repository.getFilteredMealsCategories(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> view.sendMealCategoryToDisplay(meals),
                        this::handleError
                ));
    }

    @Override
    public void getMealbycIngredient(String ingredientName) {
        if (view == null) return;

        compositeDisposable.add(repository.getFilteredMealsIngredients(ingredientName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> view.sendMealIngredientsToDisplay(meals),
                        this::handleError
                ));
    }



    @Override
    public void getMealbyCountry(String countryName) {
        if (view == null) return;

        compositeDisposable.add(repository.getFilteredMealsCountries(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> view.sendMealCountryToDisplay(meals),
                        this::handleError
                ));
    }

    @Override
    public void getMealListWithLetter(char letter) {
        if (view == null) return;

        compositeDisposable.add(repository.getMealListWithLetter(letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealResponse -> mealResponse.getMeals())
                .subscribe(
                        meals -> view.displayMealListWithLetter(meals),
                        this::handleError
                ));
    }

    private void handleError(Throwable throwable) {
        if (view != null) {
            view.showError("Something went wrong. Please try again.");
        }
    }

    public void clear() {
        compositeDisposable.dispose(); // Disposes of all ongoing network requests
    }
}
