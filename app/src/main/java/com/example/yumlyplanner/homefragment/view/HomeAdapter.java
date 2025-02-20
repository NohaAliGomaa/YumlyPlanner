package com.example.yumlyplanner.homefragment.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Ingredient;

import java.util.ArrayList;
import java.util.List;

// Ensure T extends BindableItem
public class HomeAdapter<T extends BindableItem> extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<T> items = new ArrayList<>();;


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
    public HomeAdapter(List<T> items) {
        this.items = items;
    }

    public void setItems(List<T> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }


    class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final ImageView itemImage;

        @SuppressLint("WrongViewCast")
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.mealTitle);
            itemImage = itemView.findViewById(R.id.mealPhoto);
        }

        // Bind the data directly using T
        void bind(T item) {

            if(item instanceof Area){
                itemTitle.setText(item.getTitle());
                itemImage.setImageResource(((Area) item).getImageResourceId());
            }
            else {
                itemTitle.setText(item.getTitle());
                Glide.with(itemView.getContext())
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.placeholder_image) // Optional: Placeholder while loading
                        .error(R.drawable.error_image) // Optional: Error image if load fails
                        .into(itemImage);
            }
        }
    }
}
