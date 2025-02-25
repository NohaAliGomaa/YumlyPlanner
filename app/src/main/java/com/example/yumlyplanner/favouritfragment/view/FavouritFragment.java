package com.example.yumlyplanner.favouritfragment.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.calendarfragmeng.view.CalendarAdapter;
import com.example.yumlyplanner.calendarfragmeng.view.CalenderragmentDirections;
import com.example.yumlyplanner.calendarfragmeng.view.OnMealClickListener;
import com.example.yumlyplanner.favouritfragment.presenter.FavoritePresenterImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavouritFragment extends Fragment implements FavoriteView, OnMealClickListener {

 private RecyclerView favRecycleView;
 private ImageView back;
 private FavoritePresenterImpl presenter;
 private  List<Meal> meals;
 private  FavoriteAdapter favoriteAdapter;
    public FavouritFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FavoritePresenterImpl(this, MealLocalDataSource.getInstance(this.getContext()));
        favRecycleView = view.findViewById(R.id.fav_RV);
        favRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        favRecycleView.setHasFixedSize(false);
        meals = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(requireContext(), meals, this);
        favRecycleView.setAdapter(favoriteAdapter);
        presenter.getAllFavouritMeal();
        back=view.findViewById(R.id.back);
        back.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_favouritFragment_to_homeFragment));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(getContext(), "Success: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealsLoaded(List<Meal> meals) {
        this.meals.clear();
        this.meals.addAll(meals);
       favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMealClick(Meal meal) {
        FavouritFragmentDirections.ActionFavouritFragmentToMealFragment action =
                FavouritFragmentDirections.actionFavouritFragmentToMealFragment (meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDeleteMeal(Meal meal) {
        new AlertDialog.Builder(this.getContext(), R.style.CustomAlertDialog)
                .setTitle("Remove Favorite?")
                .setMessage("Are you sure you want to remove this meal from favorites?")
                .setPositiveButton("Yes", (dialogInterface, which) -> {
                    presenter.deletMealFromFavourit(meal.getIdMeal());
                    favoriteAdapter.notifyDataSetChanged();
                    Toast.makeText(this.getContext(), "Meal removed from favorites", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}