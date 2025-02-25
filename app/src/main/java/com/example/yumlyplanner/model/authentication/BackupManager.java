package com.example.yumlyplanner.model.authentication;

import android.util.Log;

import com.example.yumlyplanner.model.MealRepositry;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackupManager {
    private static final String COLLECTION_NAME = "meal_backups";
    private final FirebaseFirestore firestore;
    private final MealRepositry repositry;
    private final Gson gson = new Gson();

    public BackupManager(MealLocalDataSource localDataSource) {
        this.firestore = FirebaseFirestore.getInstance();
        repositry=MealRepositry.getInstance(localDataSource);
    }

    public void backupMeals(List<Meal> meals, String userId) {
        if (userId == null) {
            Log.e("Backup", "User ID is null, cannot backup meals.");
            return;
        }
        Log.e("Backup", "Starting backup...");

        Map<String, Object> mealBackup = new HashMap<>();

        for (Meal meal : meals) {
            Map<String, Object> mealDetails = new HashMap<>();
            mealDetails.put("mealId",meal.getMealId());
            mealDetails.put("idMeal", meal.getIdMeal());
            mealDetails.put("strMeal", meal.getStrMeal());
            mealDetails.put("strDrinkAlternate", meal.getStrDrinkAlternate());
            mealDetails.put("strCategory", meal.getStrCategory());
            mealDetails.put("strArea", meal.getStrArea());
            mealDetails.put("strInstructions", meal.getStrInstructions());
            mealDetails.put("strMealThumb", meal.getStrMealThumb());
            mealDetails.put("strTags", meal.getStrTags());
            mealDetails.put("strYoutube", meal.getStrYoutube());
            mealDetails.put("strSource", meal.getStrSource());
            mealDetails.put("date", meal.getDate());
            mealDetails.put("isFavourit", meal.isFavourit());

            for (int i = 1; i <= 20; i++) {
                try {
                    String ingredient = (String) Meal.class.getDeclaredField("strIngredient" + i).get(meal);
//                    String measure = (String) Meal.class.getDeclaredField("strMeasure" + i).get(meal);
                    mealDetails.put("MealIngredient" + i, ingredient);
//                    mealDetails.put("MealMeasure" + i, measure);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 20; i++) {
                try {
//                    String ingredient = (String) Meal.class.getDeclaredField("strIngredient" + i).get(meal);
                    String measure = (String) Meal.class.getDeclaredField("strMeasure" + i).get(meal);
//                    mealDetails.put("MealIngredient" + i, ingredient);
                    mealDetails.put("MealMeasure" + i, measure);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


            mealBackup.put(meal.getIdMeal(), mealDetails);
        }

        firestore.collection(COLLECTION_NAME)
                .document(userId)
                .set(mealBackup, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("Backup", "Backup successful for user: " + userId))
                .addOnFailureListener(e -> Log.e("Backup", "Backup failed", e));
    }
    public void restoreMeals(String userId) {
        if (userId == null || "GUEST".equals(userId)) {
            Log.e("Restore", "User ID is null, cannot restore meals.");
            return;
        }

        firestore.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> mealBackup = documentSnapshot.getData();
                        Log.d("Restore", "Meal Backup Data: " + mealBackup); // Debugging

                        if (mealBackup != null) {
                            List<Meal> restoredMeals = new ArrayList<>();

                            for (Map.Entry<String, Object> entry : mealBackup.entrySet()) {
                                if (entry.getValue() instanceof Map) {
                                    Map<String, Object> mealData = (Map<String, Object>) entry.getValue();

                                    Meal meal = new Meal();
                                    Object mealIdObj = mealData.get("mealId");
                                    int mealId = 0; // Default value

                                    if (mealIdObj instanceof Number) {
                                        mealId = ((Number) mealIdObj).intValue(); // Convert Long/Double to int safely
                                    }

                                    meal.setMealId(mealId);
                                    meal.setIdMeal((String) mealData.getOrDefault("idMeal", ""));
                                    meal.setStrMeal((String) mealData.getOrDefault("strMeal", ""));
                                    meal.setStrDrinkAlternate((String) mealData.getOrDefault("strDrinkAlternate", ""));
                                    meal.setStrCategory((String) mealData.getOrDefault("strCategory", ""));
                                    meal.setStrArea((String) mealData.getOrDefault("strArea", ""));
                                    meal.setStrInstructions((String) mealData.getOrDefault("strInstructions", ""));
                                    meal.setStrMealThumb((String) mealData.getOrDefault("strMealThumb", ""));
                                    meal.setStrTags((String) mealData.getOrDefault("strTags", ""));
                                    meal.setStrYoutube((String) mealData.getOrDefault("strYoutube", ""));
                                    meal.setStrSource((String) mealData.getOrDefault("strSource", ""));
                                    meal.setDate((String) mealData.getOrDefault("date", ""));
                                    meal.setFavourit(mealData.get("isFavourit") != null && (boolean) mealData.get("isFavourit"));

                                    for (int i = 1; i <= 20; i++) {
                                        try {
                                            String ingredient = (String) mealData.get("MealIngredient" + i);
//                                            String measure = (String) mealData.get("MealMeasure" + i);
                                                Meal.class.getDeclaredField("strIngredient" + i).set(meal, ingredient);
//                                                Meal.class.getDeclaredField("strMeasure" + i).set(meal, measure);
                                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                            Log.e("Restore", "Reflection error for ingredient " + i, e);
                                        }
                                    }

                                    for (int i = 1; i <= 20; i++) {
                                        try {
//                                            String ingredient = (String) mealData.get("MealIngredient" + i);
                                            String measure = (String) mealData.get("MealMeasure" + i);
//                                            Meal.class.getDeclaredField("strIngredient" + i).set(meal, ingredient);
                                                Meal.class.getDeclaredField("strMeasure" + i).set(meal, measure);
                                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                            Log.e("Restore", "Reflection error for ingredient " + i, e);
                                        }
                                    }
                                    restoredMeals.add(meal);
                                }
                            }
                            for(Meal meal:restoredMeals)
                            { Log.d("Restore", "Meal from insertMeals inserted: " + meal.getStrMeal());
//                                repositry.insert(meal);
                                repositry.insert(meal)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
//                                                () -> view.onSuccess("the Meal is Set to be Favourit"),
//                                                throwable -> handleError(throwable)
                                        );
                            }

                            Log.d("Restore", "Meals restored successfully: " + restoredMeals.size());
                        } else {
                            Log.e("Restore", "No meal backup found in Firestore.");
                        }
                    } else {
                        Log.e("Restore", "No document found for userId: " + userId);
                    }
                })
                .addOnFailureListener(e -> Log.e("Restore", "Failed to restore meals", e));
    }

}
