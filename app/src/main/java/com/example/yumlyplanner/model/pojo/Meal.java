package com.example.yumlyplanner.model.pojo;

import java.util.List;

public class Meal {
    private String idMeal;
    private String strMeal;
    private String strDrinkAlternate;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strTags;
    private String strYoutube;
    private String strSource;
    private List<Ingredient> ingredients;
    private List<String> measures;

    public Meal(String idMeal, String strMeal, String strDrinkAlternate, String strCategory, String strArea,
                String strInstructions, String strMealThumb, String strTags, String strYoutube,
                String strSource, List<Ingredient> ingredients, List<String> measures) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strDrinkAlternate = strDrinkAlternate;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strTags = strTags;
        this.strYoutube = strYoutube;
        this.strSource = strSource;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    // Getters and Setters
    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }

    public String getStrDrinkAlternate() { return strDrinkAlternate; }
    public void setStrDrinkAlternate(String strDrinkAlternate) { this.strDrinkAlternate = strDrinkAlternate; }

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }

    public String getStrArea() { return strArea; }
    public void setStrArea(String strArea) { this.strArea = strArea; }

    public String getStrInstructions() { return strInstructions; }
    public void setStrInstructions(String strInstructions) { this.strInstructions = strInstructions; }

    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }

    public String getStrTags() { return strTags; }
    public void setStrTags(String strTags) { this.strTags = strTags; }

    public String getStrYoutube() { return strYoutube; }
    public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }

    public String getStrSource() { return strSource; }
    public void setStrSource(String strSource) { this.strSource = strSource; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public List<String> getMeasures() { return measures; }
    public void setMeasures(List<String> measures) { this.measures = measures; }
}

