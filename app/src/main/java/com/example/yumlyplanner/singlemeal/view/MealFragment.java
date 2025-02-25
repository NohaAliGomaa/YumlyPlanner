package com.example.yumlyplanner.singlemeal.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.homefragment.view.HomeAdapter;
import com.example.yumlyplanner.loginfragment.view.LoginFragment;
import com.example.yumlyplanner.model.authentication.SessionManager;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.pojo.Area;
import com.example.yumlyplanner.model.pojo.Meal;
import com.example.yumlyplanner.singlemeal.presenter.SingleMealPresenterImpl;

import java.time.Year;

public class MealFragment extends Fragment implements SingleMealView{
    private ImageView mealPhotoView, addToFavouritBtn, backToHome;
    private TextView mealTitle, mealCategory, mealArea, Ingredients, mealInstruction;
    private WebView mealVideo;
    private RecyclerView ingredientRV;
    private Button addToCalenderBtn;
     private Meal theMeal;
     private String pickedDate;
     private SingleMealPresenterImpl presenter;

    private static final String TAG = "MealFragment";

    public MealFragment() {
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
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter=new SingleMealPresenterImpl(this, MealLocalDataSource.getInstance(this.getContext()));
        mealPhotoView = view.findViewById(R.id.mealPhotoView);
        addToFavouritBtn = view.findViewById(R.id.addToFavouritBtn);
        backToHome = view.findViewById(R.id.backToHome);
        mealTitle = view.findViewById(R.id.mealTitle);
        mealCategory = view.findViewById(R.id.mealCategory);
        mealArea = view.findViewById(R.id.mealArea);
        Ingredients = view.findViewById(R.id.Ingredients);
        mealInstruction = view.findViewById(R.id.mealInstruction);
        mealVideo = view.findViewById(R.id.mealVideo);
        mealVideo.getSettings().setJavaScriptEnabled(true);
        mealVideo.getSettings().setDomStorageEnabled(true);
        ingredientRV = view.findViewById(R.id.ingredientRV);
        ingredientRV.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        ingredientRV.setHasFixedSize(true);
        addToCalenderBtn = view.findViewById(R.id.addToCalenderBtn);
        String mealId;
        MealFragmentArgs args=MealFragmentArgs.fromBundle(getArguments());
        mealId=args.getMealId();
        backToHome.setOnClickListener(v ->
                Navigation.findNavController(requireView()).navigate(R.id.action_mealFragment_to_homeFragment));
        addToFavouritBtn.setOnClickListener(v -> {
            if (SessionManager.getInstance(this.getContext()).isGuestMode()) {
                showLoginPrompt(view);
                return;
            }
            if (theMeal == null) return;

            if (!theMeal.isFavourit()) {
                theMeal.setFavourit(true);
                presenter.addtoFavourit(theMeal);
            } else {
                Log.d(TAG, "Deleting meal: " + theMeal.getMealId());
                presenter.deletMealFromFavourit(theMeal.getIdMeal());
                theMeal.setFavourit(false);
            }
            updateFavouriteButton();
        });

        addToCalenderBtn.setOnClickListener(v -> {
            if (SessionManager.getInstance(this.getContext()).isGuestMode()) {
                showLoginPrompt(view);
                return;
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            pickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                            if (pickedDate == null) return;

                            if (pickedDate != null && !theMeal.isFavourit()) {
                                theMeal.setDate(pickedDate);
                                presenter.addtoFavourit(theMeal);
                            } else {
                                presenter.updateMealDate(theMeal.getIdMeal(), pickedDate);
                            }// Month is 0-based
                        }
                    }, 2025, 1, 20);

            datePickerDialog.show();
            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.primeryColor));
            datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.primeryColor));



        });
        presenter.getDetailedMeal(mealId);
    }


    private void updateFavouriteButton() {
        if (theMeal != null) {
            Log.d(TAG, "Meal Favourite Status: " + theMeal.isFavourit());

            if (theMeal.isFavourit()) {
                addToFavouritBtn.setImageResource(R.drawable.heart);
            } else {
                addToFavouritBtn.setImageResource(R.drawable.love);
            }

            // Force the ImageView to refresh
            addToFavouritBtn.invalidate();
        }
    }
    @Override
    public void displayMealInScreen(Meal meal) {
        Toast.makeText(requireContext(), "the meal hase been retrive"+meal.getStrMeal(), Toast.LENGTH_SHORT).show();
        theMeal=meal;
        Glide.with(this).load(meal.getStrMealThumb())
                .error(R.drawable.error_image).into(mealPhotoView);
        mealTitle.setText(meal.getStrMeal() != null ? meal.getStrMeal() : "Unknown");
        mealCategory.setText(meal.getStrCategory() != null ? meal.getStrCategory() : "Unknown");
        mealArea.setText(meal.getStrArea() != null ? meal.getStrArea() : "Unknown");
        if (meal.getIngredients() != null && meal.getMeasures() != null) {
            MealAdapter adapter = new MealAdapter(meal.getIngredients(), meal.getMeasures());
            ingredientRV.setAdapter(adapter);
        } else {
            Log.e(TAG, "Meal ingredients or measures are null!");
        }
        mealInstruction.setText(meal.getStrInstructions());
        if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {
            loadYoutubeVideo(meal.getStrYoutube());
        }
        updateFavouriteButton();

    }
    private void loadYoutubeVideo(String videoUrl) {
        String videoId = extractYoutubeVideoId(videoUrl);

        if (videoId != null) {
            String embedHtml = "<html><body style='margin:0;padding:0;'>" +
                    "<iframe width='100%' height='100%' " +
                    "src='https://www.youtube.com/embed/" + videoId + "' " +
                    "frameborder='0' allowfullscreen></iframe></body></html>";

            mealVideo.getSettings().setJavaScriptEnabled(true);
            mealVideo.loadData(embedHtml, "text/html", "utf-8");
        }
    }
    private String extractYoutubeVideoId(String youtubeUrl) {
        if (youtubeUrl != null && youtubeUrl.contains("v=")) {
            return youtubeUrl.split("v=")[1].split("&")[0]; // Extract Video ID
        }
        return null;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.calenderFragment).setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    private void showLoginPrompt(View view) {
        new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog)
                .setTitle("Sign in required")
                .setMessage("Create an account or log in to save and plan meals.")
                .setPositiveButton("Sign In", (dialogInterface, which) -> {
                    // Navigate to login/register fragment
                    Navigation.findNavController(view).navigate(R.id.action_mealFragment_to_registerFragment);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


}