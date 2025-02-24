package com.example.yumlyplanner.calendarfragmeng.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.CalendarView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.calendarfragmeng.presenter.CalendarPresenter;
import com.example.yumlyplanner.calendarfragmeng.presenter.CalendarPresenterImpl;
import com.example.yumlyplanner.homefragment.view.HomeFragmentDirections;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.singlemeal.view.MealAdapter;

import java.util.ArrayList;
import java.util.List;

public class Calenderragment extends Fragment implements com.example.yumlyplanner.calendarfragmeng.view.CalendarView, OnMealClickListener {

    private CalendarView calendarView;
    private RecyclerView calendarRecyclerView;
    private CalendarPresenterImpl presenter;
    private List<Meal> meals;
    private CalendarAdapter calendarAdapter;

    public Calenderragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calenderragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CalendarPresenterImpl(this, MealLocalDataSource.getInstance(requireContext()));

        calendarView = view.findViewById(R.id.calendarView);
        calendarRecyclerView = view.findViewById(R.id.calendar_RV);
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with empty list
        meals = new ArrayList<>();
        calendarAdapter = new CalendarAdapter(requireContext(), meals, this);
        calendarRecyclerView.setAdapter(calendarAdapter);

        calendarView.setFocusedMonthDateColor(ContextCompat.getColor(requireContext(), R.color.primeryColor));
        calendarView.setSelectedWeekBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primeryColor));
        calendarView.setUnfocusedMonthDateColor(ContextCompat.getColor(requireContext(), R.color.primeryColor));

        // Handle date selection
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = year + "." + (month +1) + "." + dayOfMonth;

            Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            presenter.getMealsByDate(selectedDate);
        });

        // Load all planned meals initially
        presenter.getAllPlanedMeal();
    }

    @Override
    public void onMealsLoaded(List<Meal> meals) {
        this.meals.clear();
        this.meals.addAll(meals);
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMealClick(Meal meal) {
        CalenderragmentDirections.ActionCalenderFragmentToMealFragment action =
                CalenderragmentDirections.actionCalenderFragmentToMealFragment(meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDeleteMeal(Meal meal) {
        presenter.updateMealDate(meal.getIdMeal(), null);
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(getContext(), "Success: " + message, Toast.LENGTH_SHORT).show();
    }
}
