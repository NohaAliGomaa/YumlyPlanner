package com.example.yumlyplanner.singlemeal.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.homefragment.view.BindableItem;
import com.example.yumlyplanner.homefragment.view.OnHomeRecycleClick;
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>  {
    private List<Ingredient> items = new ArrayList<>();
    private List<String> measurs=new ArrayList<>();

    public MealAdapter(List<Ingredient> items, List<String> measurs) {
        this.items = (items != null) ? items : new ArrayList<>();
        this.measurs = (measurs != null) ? measurs : new ArrayList<>();
    }

    public void setItems(List<Ingredient> newItems, List<String> newMeasurs) {
        this.items = (newItems != null) ? newItems : new ArrayList<>();
        this.measurs = (newMeasurs != null) ? newMeasurs : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_ingredient_with_measur, parent, false);
        return new MealAdapter.MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.MealViewHolder holder, int position) {
        if (items.isEmpty() || measurs.isEmpty()) {
            Log.e("MealAdapter", "Empty ingredients or measures list");
            return;
        }
        holder.bind(items.get(position), measurs.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.max(items.size(), measurs.size()); // Avoid IndexOutOfBoundsException
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final TextView measure;
        private final ImageView itemImage;

        @SuppressLint("WrongViewCast")
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.ingredientTittel);
            measure=itemView.findViewById(R.id.measure);
            itemImage = itemView.findViewById(R.id.ingredientImage);

        }

        // Bind the data directly using T
        void bind(Ingredient item, String measur) {
            if (item != null) {
                itemTitle.setText(item.getTitle() != null ? item.getTitle() : "Unknown Ingredient");
                measure.setText(measur != null ? measur : "N/A");

                Glide.with(itemView.getContext())
                        .load(item.getImageUrl() != null ? item.getImageUrl() : R.drawable.placeholder_image)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(itemImage);
            }
        }
    }
}
