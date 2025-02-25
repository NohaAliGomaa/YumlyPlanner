package com.example.yumlyplanner.model.authentication;



import androidx.room.TypeConverter;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class MealTypeConverter {
    private static final Gson gson = new Gson();

    // Convert List<Ingredient> to JSON String
    @TypeConverter
    public static String fromIngredientList(List<Ingredient> ingredients) {
        return ingredients == null ? null : gson.toJson(ingredients);
    }

    // Convert JSON String to List<Ingredient>
    @TypeConverter
    public static List<Ingredient> toIngredientList(String ingredientJson) {
        if (ingredientJson == null) return null;
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(ingredientJson, type);
    }

    // Convert List<String> (measures) to JSON String
    @TypeConverter
    public static String fromStringList(List<String> measures) {
        return measures == null ? null : gson.toJson(measures);
    }

    // Convert JSON String back to List<String>
    @TypeConverter
    public static List<String> toStringList(String measureJson) {
        if (measureJson == null) return null;
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(measureJson, type);
    }
}

