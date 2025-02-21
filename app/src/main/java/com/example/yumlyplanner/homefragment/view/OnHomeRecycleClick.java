package com.example.yumlyplanner.homefragment.view;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OnHomeRecycleClick {
public  void categoryName(String category);
public  void  IngrsdientName(String ingredient);
public void  countryName(String country);
public void  navigateMeal();
}
