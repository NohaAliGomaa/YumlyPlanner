package com.example.yumlyplanner.searchfragment.view;

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
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter<T extends BindableItem> extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<T> items = new ArrayList<>();
    private final OnSearchRecycleClick onSearchRecycleClick;

    public SearchAdapter(List<T> items, OnSearchRecycleClick onSearchRecycleClick) {
        this.items = new ArrayList<>(items);
        this.onSearchRecycleClick = onSearchRecycleClick;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyItemRangeChanged(0, items.size());
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final ImageView itemImage;
        private final CardView cardView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.mealTitle);
            itemImage = itemView.findViewById(R.id.mealPhotoView);
            cardView = itemView.findViewById(R.id.itemCard);
        }

        void bind(T item) {
            itemTitle.setText(item.getTitle());

            if (item instanceof Area) {
                itemImage.setImageResource(((Area) item).getImageResourceId());
                cardView.setOnClickListener(v -> onSearchRecycleClick.countryName(item.getTitle()));
            } else {
                Glide.with(itemView.getContext())
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(itemImage);

                if (item instanceof Ingredient) {
                    cardView.setOnClickListener(v -> onSearchRecycleClick.IngrsdientName(item.getTitle()));
                } else if (item instanceof Category) {
                    cardView.setOnClickListener(v -> onSearchRecycleClick.categoryName(item.getTitle()));
                }else if (item instanceof Meal){
                    cardView.setOnClickListener(v -> onSearchRecycleClick.navigateMeal(((Meal) item).getIdMeal()));
                }
            }
        }
    }
}
