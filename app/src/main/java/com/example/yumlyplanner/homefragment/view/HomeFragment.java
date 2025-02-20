package com.example.yumlyplanner.homefragment.view;

import static com.bumptech.glide.Glide.*;

import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.homefragment.presenter.HomePresenterImpl;
import com.example.yumlyplanner.model.pojo.Meal;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class HomeFragment extends Fragment implements HomeView{
    private static final String TAG = "HomeFragment";
    private HomePresenterImpl presenter;
    TextView mealTitle,instruction;
    ImageView mealPhoto;
    private Chip categoryChip, ingredientChip, countryChip;
    private ChipGroup chipGroup;

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
        chipGroup = view.findViewById(R.id.chip_group);
        categoryChip = view.findViewById(R.id.category_chip);
        ingredientChip = view.findViewById(R.id.ingredient_chip);
        countryChip = view.findViewById(R.id.country_chip);
        instruction=view.findViewById(R.id.mealInstruction);
        chipGroup.setOnCheckedChangeListener((chipGroup, id) -> {
            Chip chip = ((Chip) chipGroup.getChildAt(chipGroup.getCheckedChipId()));
            if (chip != null) {
                for (int i = 0; i < chipGroup.getChildCount(); ++i) {
                    chipGroup.getChildAt(i).setClickable(true);
                }
                chip.setClickable(false);
            }
        });
       presenter.getRandomMeal();
    }
    private void setChipClickListener(Chip chip) {
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chip.isChecked()) {
                    chip.setChipBackgroundColorResource(R.color.primeryColor); // Change to selected color
                } else {
                    chip.setChipBackgroundColorResource(R.color.toneColor); // Change back to default
                }
            }
        });
    }
    @Override
    public void showRandomMeal(Meal meal) {
        if (!isAdded()) {  // Prevent updates on a detached fragment
            return;
        }
        if (meal != null) {
            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealPhoto);
            mealTitle.setText(meal.getStrMeal());
            instruction.setText(meal.getStrInstructions());
            Log.i(TAG, "showRandomMeal: " + meal.getStrMeal() + " is here");
        } else {
            Log.e(TAG, "showRandomMeal: Meal is null");
        }

        Context context = getContext();
        if (context != null) {
            Toast.makeText(context, "Random meal displayed", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        requireActivity().findViewById(R.id.calenderFragment).setVisibility(View.VISIBLE);
    }
}