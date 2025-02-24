package com.example.yumlyplanner.calendarfragmeng.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;

import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MealViewHolder> {

    private List<Meal> mealList;
    private Context context;
    private OnMealClickListener listener;

    public CalendarAdapter(Context context, List<Meal> mealList, OnMealClickListener listener) {
        this.context = context;
        this.mealList = mealList;
        this.listener = listener;
    }

    public void setMeals(List<Meal> meals) {
        this.mealList = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_meal_layout, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.bind(meal,listener);
    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView mealCard;
        ImageView mealImage, cancelImage;
        TextView mealTitle, mealDate;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealCard = itemView.findViewById(R.id.calenderCard);
            mealImage = itemView.findViewById(R.id.mealCalendar);
            cancelImage = itemView.findViewById(R.id.imageView);
            mealTitle = itemView.findViewById(R.id.mealCalendarTitle);
            mealDate = itemView.findViewById(R.id.mealDate);
        }
        void bind(Meal item,OnMealClickListener listener) {
            if (item != null) {
               mealTitle.setText(item.getTitle() != null ? item.getTitle() : "Unknown Meal");
                mealDate.setText(item.getDate());

                Glide.with(itemView.getContext())
                        .load(item.getImageUrl() != null ? item.getImageUrl() : R.drawable.placeholder_image)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(mealImage);
                cancelImage.setOnClickListener(v-> listener.onDeleteMeal(item));
                mealCard.setOnClickListener(v->listener.onMealClick(item));
            }
        }
    }
}
