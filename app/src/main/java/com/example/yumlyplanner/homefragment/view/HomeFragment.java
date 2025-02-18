package com.example.yumlyplanner.homefragment.view;

import static com.bumptech.glide.Glide.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.homefragment.presenter.HomePresenterImpl;
import com.example.yumlyplanner.model.pojo.Meal;

public class HomeFragment extends Fragment implements HomeView{
    private static final String TAG = "HomeFragment";
    private HomePresenterImpl presenter;
    TextView mealTitle,instruction;
    ImageView mealPhoto;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter=new HomePresenterImpl(this);
        mealTitle=view.findViewById(R.id.mealTitle);
        mealPhoto=view.findViewById(R.id.mealPhoto);
        instruction=view.findViewById(R.id.mealInstruction);
       presenter.getRandomMeal();
    }

    @Override
    public void showRandomMeal(Meal meal) {
        if (meal != null) {
            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealPhoto);
            mealTitle.setText(meal.getStrMeal());
            instruction.setText(meal.getStrInstructions());
            Log.i(TAG, "showRandomMeal: " + meal.getStrMeal() + " is here");
        } else {
            Log.e(TAG, "showRandomMeal: Meal is null");
        }
    }
}