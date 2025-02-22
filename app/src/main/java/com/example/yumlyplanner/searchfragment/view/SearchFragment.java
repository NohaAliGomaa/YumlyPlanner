package com.example.yumlyplanner.searchfragment.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Category;
import com.example.yumlyplanner.model.pojo.Ingredient;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.model.response.AllArea;
import com.example.yumlyplanner.searchfragment.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment implements SearchView, OnSearchRecycleClick {
    private SearchPresenterImpl presenter;
    private TextInputEditText search;
    private Button back;
    private Chip categoryChip, ingredientChip, countryChip;
    private ChipGroup chipGroup;
    private RecyclerView recyclerView;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SearchPresenterImpl(this, this , MealLocalDataSource.getInstance(getContext()) );

        recyclerView = view.findViewById(R.id.home_RV);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(false);

        search = view.findViewById(R.id.et_Search);
        back = view.findViewById(R.id.backBtn);
        chipGroup = view.findViewById(R.id.chip_group);
        categoryChip = view.findViewById(R.id.category_chip);
        ingredientChip = view.findViewById(R.id.ingredient_chip);
        countryChip = view.findViewById(R.id.country_chip);

        chipGroup.setOnCheckedChangeListener((chipGroup, id) -> {
            if (id != -1) { // Ensure a chip is selected
                Chip chip = view.findViewById(id);
                if (chip != null) {
                    for (int i = 0; i < chipGroup.getChildCount(); ++i) {
                        chipGroup.getChildAt(i).setClickable(true);
                    }
                    chip.setClickable(false);
                }
            }
        });

        countryChip.setOnClickListener(v -> {
            List<Area> areas = AllArea.getInstance().getAllArea();
            SearchAdapter<Area> adapter = new SearchAdapter<>(areas, this);
            recyclerView.setAdapter(adapter);
        });

        ingredientChip.setOnClickListener(v -> presenter.getIngredient());
        categoryChip.setOnClickListener(v -> presenter.getAllCategories());

       setWatcherForSearch(search);

        back.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_homeFragment));
    }

    private void setWatcherForSearch(EditText search) {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) { // Prevent crash when empty
                    presenter.getMealListWithLetter(s.charAt(0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void showIngredient(List<Ingredient> ingredients) {
        SearchAdapter<Ingredient> adapter = new SearchAdapter<>(ingredients, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showCategory(List<Category> categories) {
        SearchAdapter<Category> adapter = new SearchAdapter<>(categories, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void sendMealCountryToDisplay(List<Meal> meals) {
        SearchAdapter<Meal> adapter = new SearchAdapter<>(meals, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void sendMealIngredientsToDisplay(List<Meal> meals) {
        SearchAdapter<Meal> adapter = new SearchAdapter<>(meals, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void sendMealCategoryToDisplay(List<Meal> meals) {
        SearchAdapter<Meal> adapter = new SearchAdapter<>(meals, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayMealListWithLetter(List<Meal> meals) {
        SearchAdapter<Meal> adapter = new SearchAdapter<>(meals, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
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
    public void letterChar(char letter) {
        presenter.getMealListWithLetter(letter);
    }

    @Override
    public void navigateMeal() {
        Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_mealFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear(); // Cancel pending API calls to prevent crashes
        compositeDisposable.clear(); // Dispose RxJava subscriptions
        presenter = null;
    }
}
