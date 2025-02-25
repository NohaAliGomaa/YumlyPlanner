package com.example.yumlyplanner.favouritfragment.view;

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
import com.example.yumlyplanner.calendarfragmeng.view.OnMealClickListener;
import com.example.yumlyplanner.model.pojo.Meal;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MealViewHolder> {

    private List<Meal> mealList;
    private Context context;
    private OnMealClickListener listener;

    public FavoriteAdapter(Context context, List<Meal> mealList, OnMealClickListener listener) {
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
    public FavoriteAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_meal, parent, false);
        return new FavoriteAdapter.MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MealViewHolder holder, int position) {
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
                mealDate.setText(item.getStrInstructions());

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
