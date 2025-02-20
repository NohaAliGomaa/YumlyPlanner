package com.example.yumlyplanner.homefragment.presenter;

import com.example.yumlyplanner.model.CategoryResponse;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenter {
    public void getRandomMeal();
    public void getIngredient();
    public  void getAllCategories();

}
