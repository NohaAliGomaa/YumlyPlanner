package com.example.yumlyplanner.homefragment.view;

import android.annotation.SuppressLint;
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
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.ArrayList;
import java.util.List;

// Ensure T extends BindableItem
public class HomeAdapter<T extends BindableItem> extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<T> items = new ArrayList<>();
    private  OnHomeRecycleClick onHomeRecycleClick;
    public HomeAdapter(List<T> items ,OnHomeRecycleClick onHomeRecycleClick) {
        this.items = items;
        this.onHomeRecycleClick=onHomeRecycleClick;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {

        holder.bind(items.get(position));
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setItems(List<T> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
    class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final ImageView itemImage;
        private  final CardView cardView;
        @SuppressLint("WrongViewCast")
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.mealTitle);
            itemImage = itemView.findViewById(R.id.mealPhotoView);
            cardView=itemView.findViewById(R.id.itemCard);
        }
        // Bind the data directly using T
        void bind(T item) {

            if(item instanceof Area){
                itemTitle.setText(item.getTitle());
                itemImage.setImageResource(((Area) item).getImageResourceId());
                cardView.setOnClickListener(v->onHomeRecycleClick.countryName(item.getTitle()));
            }
            else {
                itemTitle.setText(item.getTitle());
                Glide.with(itemView.getContext())
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.placeholder_image) // Optional: Placeholder while loading
                        .error(R.drawable.error_image) // Optional: Error image if load fails
                        .into(itemImage);
                if(item instanceof Ingredient) {
                    cardView.setOnClickListener(v -> onHomeRecycleClick.IngrsdientName(item.getTitle()));
                }
                else if(item instanceof Category) {
                    cardView.setOnClickListener(v -> onHomeRecycleClick.categoryName(item.getTitle()));
                }
                else if(item instanceof Meal){
                    cardView.setOnClickListener(v->onHomeRecycleClick.navigateMeal(((Meal) item).getIdMeal()));
                }
            }

        }
    }
}
