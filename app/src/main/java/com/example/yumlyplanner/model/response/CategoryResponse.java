package com.example.yumlyplanner.model.response;

import com.example.yumlyplanner.model.pojo.Category;

import java.util.ArrayList;

public class CategoryResponse {
    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
