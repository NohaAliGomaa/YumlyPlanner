package com.example.yumlyplanner.model.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yumlyplanner.homefragment.view.BindableItem;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "meals")
public class Meal  implements BindableItem {
    @PrimaryKey(autoGenerate = true)
    private int mealId;

    @ColumnInfo(name = "id_meal")
    private String idMeal;

    @ColumnInfo(name = "name")
    private String strMeal;

    @ColumnInfo(name = "drink_alternate")
    private String strDrinkAlternate;

    @ColumnInfo(name = "category")
    private String strCategory;

    @ColumnInfo(name = "area")
    private String strArea;

    @ColumnInfo(name = "instructions")
    private String strInstructions;

    @ColumnInfo(name = "meal_thumb")
    private String strMealThumb;

    @ColumnInfo(name = "tags")
    private String strTags;

    @ColumnInfo(name = "youtube")
    private String strYoutube;

    @ColumnInfo(name = "source")
    private String strSource;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "MealIngredient1")
    public String strIngredient1;

    @ColumnInfo(name = "MealIngredient2")
    public String strIngredient2;

    @ColumnInfo(name = "MealIngredient3")
    public String strIngredient3;

    @ColumnInfo(name = "MealIngredient4")
    public String strIngredient4;

    @ColumnInfo(name = "MealIngredient5")
    public String strIngredient5;

    @ColumnInfo(name = "MealIngredient6")
    public String strIngredient6;

    @ColumnInfo(name = "MealIngredient7")
    public String strIngredient7;

    @ColumnInfo(name = "MealIngredient8")
    public String strIngredient8;

    @ColumnInfo(name = "MealIngredient9")
    public String strIngredient9;

    @ColumnInfo(name = "MealIngredient10")
    public String strIngredient10;

    @ColumnInfo(name = "MealIngredient11")
    public String strIngredient11;

    @ColumnInfo(name = "MealIngredient12")
    public String strIngredient12;

    @ColumnInfo(name = "MealIngredient13")
    public String strIngredient13;

    @ColumnInfo(name = "MealIngredient14")
    public String strIngredient14;

    @ColumnInfo(name = "MealIngredient15")
    public String strIngredient15;

    @ColumnInfo(name = "MealIngredient16")
    public String strIngredient16;

    @ColumnInfo(name = "MealIngredient17")
    public String strIngredient17;

    @ColumnInfo(name = "MealIngredient18")
    public String strIngredient18;

    @ColumnInfo(name = "MealIngredient19")
    public String strIngredient19;

    @ColumnInfo(name = "MealIngredient20")
    public String strIngredient20;


    @ColumnInfo(name = "MealMeasure1")
    public String strMeasure1;

    @ColumnInfo(name = "MealMeasure2")
    public String strMeasure2;

    @ColumnInfo(name = "MealMeasure3")
    public String strMeasure3;

    @ColumnInfo(name = "MealMeasure4")
    public String strMeasure4;

    @ColumnInfo(name = "MealMeasure5")
    public String strMeasure5;

    @ColumnInfo(name = "MealMeasure6")
    public String strMeasure6;

    @ColumnInfo(name = "MealMeasure7")
    public String strMeasure7;

    @ColumnInfo(name = "MealMeasure8")
    public String strMeasure8;

    @ColumnInfo(name = "MealMeasure9")
    public String strMeasure9;

    @ColumnInfo(name = "MealMeasure10")
    public String strMeasure10;

    @ColumnInfo(name = "MealMeasure11")
    public String strMeasure11;

    @ColumnInfo(name = "MealMeasure12")
    public String strMeasure12;

    @ColumnInfo(name = "MealMeasure13")
    public String strMeasure13;

    @ColumnInfo(name = "MealMeasure14")
    public String strMeasure14;

    @ColumnInfo(name = "MealMeasure15")
    public String strMeasure15;

    @ColumnInfo(name = "MealMeasure16")
    public String strMeasure16;

    @ColumnInfo(name = "MealMeasure17")
    public String strMeasure17;

    @ColumnInfo(name = "MealMeasure18")
    public String strMeasure18;

    @ColumnInfo(name = "MealMeasure19")
    public String strMeasure19;


    @ColumnInfo(name = "MealMeasure20")
    public String strMeasure20;
    @ColumnInfo(name = "isFavourit")
    public boolean isFavourit ;

    public Meal() {
    }

    // Constructor
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

    }

    public boolean isFavourit() {
        return isFavourit;
    }

    public void setFavourit(boolean favourit) {
        isFavourit = favourit;
    }

    // Getters and Setters
    public int getMealId() { return mealId; }
    public void setMealId(int mealId) { this.mealId = mealId; }

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

    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        addIngredientIfNotEmpty(ingredients, strIngredient1);
        addIngredientIfNotEmpty(ingredients, strIngredient2);
        addIngredientIfNotEmpty(ingredients, strIngredient3);
        addIngredientIfNotEmpty(ingredients, strIngredient4);
        addIngredientIfNotEmpty(ingredients, strIngredient5);
        addIngredientIfNotEmpty(ingredients, strIngredient6);
        addIngredientIfNotEmpty(ingredients, strIngredient7);
        addIngredientIfNotEmpty(ingredients, strIngredient8);
        addIngredientIfNotEmpty(ingredients, strIngredient9);
        addIngredientIfNotEmpty(ingredients, strIngredient10);
        addIngredientIfNotEmpty(ingredients, strIngredient11);
        addIngredientIfNotEmpty(ingredients, strIngredient12);
        addIngredientIfNotEmpty(ingredients, strIngredient13);
        addIngredientIfNotEmpty(ingredients, strIngredient14);
        addIngredientIfNotEmpty(ingredients, strIngredient15);
        addIngredientIfNotEmpty(ingredients, strIngredient16);
        addIngredientIfNotEmpty(ingredients, strIngredient17);
        addIngredientIfNotEmpty(ingredients, strIngredient18);
        addIngredientIfNotEmpty(ingredients, strIngredient19);
        addIngredientIfNotEmpty(ingredients, strIngredient20);
        return ingredients;
    }

    public List<String> getMeasures() {
        List<String> measures = new ArrayList<>();
        addIfNotEmpty(measures, strMeasure1);
        addIfNotEmpty(measures, strMeasure2);
        addIfNotEmpty(measures, strMeasure3);
        addIfNotEmpty(measures, strMeasure4);
        addIfNotEmpty(measures, strMeasure5);
        addIfNotEmpty(measures, strMeasure6);
        addIfNotEmpty(measures, strMeasure7);
        addIfNotEmpty(measures, strMeasure8);
        addIfNotEmpty(measures, strMeasure9);
        addIfNotEmpty(measures, strMeasure10);
        addIfNotEmpty(measures, strMeasure11);
        addIfNotEmpty(measures, strMeasure12);
        addIfNotEmpty(measures, strMeasure13);
        addIfNotEmpty(measures, strMeasure14);
        addIfNotEmpty(measures, strMeasure15);
        addIfNotEmpty(measures, strMeasure16);
        addIfNotEmpty(measures, strMeasure17);
        addIfNotEmpty(measures, strMeasure18);
        addIfNotEmpty(measures, strMeasure19);
        addIfNotEmpty(measures, strMeasure20);
        return measures;
    }

    private void addIfNotEmpty(List<String> list, String value) {
        if (value != null && !value.trim().isEmpty()) {
            list.add(value);
        }
    }
    private void addIngredientIfNotEmpty(List<Ingredient> list, String value) {
        if (value != null && !value.trim().isEmpty()) {
            list.add(new Ingredient(value));
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getTitle() {
        return getStrMeal();
    }

    @Override
    public String getImageUrl() {
        return getStrMealThumb();
    }
}

