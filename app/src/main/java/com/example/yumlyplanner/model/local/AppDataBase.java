package com.example.yumlyplanner.model.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.yumlyplanner.model.authentication.MealTypeConverter;
import com.example.yumlyplanner.model.pojo.Meal;

@Database(entities = {Meal.class}, version = 2)
public  abstract  class AppDataBase  extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract MealDao mealDAO();

    //one thread at a time to access this method
    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "meals")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
