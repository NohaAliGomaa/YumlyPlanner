package com.example.yumlyplanner.homefragment.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.homefragment.presenter.HomePresenterImpl;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.response.AllArea;
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.singlemeal.view.MealFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView,OnHomeRecycleClick{
    private static final String TAG = "HomeFragment";
    private CardView randomMeal;
    private HomePresenterImpl presenter;
    TextView mealTitle,instruction;
    ImageView mealPhoto;
    private Chip categoryChip, ingredientChip, countryChip;
    private ChipGroup chipGroup;
    RecyclerView recyclerView;

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
        presenter=new HomePresenterImpl(this,this, MealLocalDataSource.getInstance(getContext()));
        recyclerView=view.findViewById(R.id.home_RV);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mealTitle=view.findViewById(R.id.mealTitle);
        mealPhoto=view.findViewById(R.id.mealPhotoView);
        chipGroup = view.findViewById(R.id.chip_group);
        categoryChip = view.findViewById(R.id.category_chip);
        ingredientChip = view.findViewById(R.id.ingredient_chip);
        countryChip = view.findViewById(R.id.country_chip);
        instruction=view.findViewById(R.id.mealInstruction);
        randomMeal=view.findViewById(R.id.itemCard);
        chipGroup.setOnCheckedChangeListener((chipGroup, id) -> {
            Chip chip = ((Chip) chipGroup.getChildAt(chipGroup.getCheckedChipId()));
            if (chip != null) {
                for (int i = 0; i < chipGroup.getChildCount(); ++i) {
                    chipGroup.getChildAt(i).setClickable(true);
                }
                chip.setClickable(false);
            }
        });
        countryChip.setOnClickListener(v ->{
            List<Area> areas=AllArea.getInstance().getAllArea();
            HomeAdapter<Area> adapter = new HomeAdapter<>(areas,this);
            recyclerView.setAdapter(adapter);
           });
        ingredientChip.setOnClickListener(v->presenter.getIngredient());
        categoryChip.setOnClickListener(v->presenter.getAllCategories());
        presenter.getAllCategories();
       presenter.getRandomMeal();
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
            randomMeal.setOnClickListener(v->navigateMeal(meal.getIdMeal()));
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
    public void showIngredient(List<Ingredient> ingredients) {
        HomeAdapter<Ingredient> adapter = new HomeAdapter<>(ingredients,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showCategory(List<Category> categories) {
        HomeAdapter<Category> adapter = new HomeAdapter<>(categories,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), "Error"+message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        requireActivity().findViewById(R.id.calenderFragment).setVisibility(View.VISIBLE);
    }

    @Override
    public void sendMealCountryToDisplay(List<Meal> meals) {
        HomeAdapter<Meal> adapter = new HomeAdapter<>(meals,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void sendMealIngredientsToDisplay(List<Meal> meals) {
        HomeAdapter<Meal> adapter = new HomeAdapter<>(meals,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void sendMealCategoryToDisplay(List<Meal> meals ){
        HomeAdapter<Meal> adapter = new HomeAdapter<>(meals,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void categoryName(String category) {
       presenter.getMealbyCategory(category);
    }

    @Override
    public void IngrsdientName(String ingredient) {
        presenter.getMealbycIngredient(ingredient);
    }

    @Override
    public void countryName(String country) {
        presenter.getMealbyCountry(country);
    }

    @Override
    public void navigateMeal(String id) {
       HomeFragmentDirections.ActionHomeFragmentToMealFragment action=
                HomeFragmentDirections.actionHomeFragmentToMealFragment(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear(); // Cancel pending API calls to prevent crashes
    }
}