package com.example.yumlyplanner.model.local;

import androidx.room.TypeConverter;

import com.example.yumlyplanner.model.pojo.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientConverter {
    @TypeConverter
    public static String fromIngredientList(List<Ingredient> ingredients) {
        return new Gson().toJson(ingredients);
    }

    @TypeConverter
    public static List<Ingredient> toIngredientList(String data) {
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }
}
