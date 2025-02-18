package com.example.yumlyplanner.model;

import com.example.yumlyplanner.model.pojo.Meal;

import java.util.List;

public interface NetworkCallBack {
    public  void onSuccessResult(List<Meal> products);
    public  void onFailurResult(String erroMsg);
}
