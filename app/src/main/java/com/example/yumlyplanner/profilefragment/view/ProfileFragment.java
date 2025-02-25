package com.example.yumlyplanner.profilefragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.profilefragment.presenter.ProfilePresenterImpl;


public class ProfileFragment extends Fragment implements ProfileView {

    private TextView tvUserName, tvUserEmail;
    private ImageView goToFav, goToCal;
    private Button logout;
    private  ImageView back;
    private  ImageView backup;
    private ProfilePresenterImpl presenter;
    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProfilePresenterImpl(this, MealLocalDataSource.getInstance(this.getContext()),this.getContext());
        // Initialize UI components
        tvUserName = view.findViewById(R.id.tv_UserName);
        tvUserEmail = view.findViewById(R.id.tv_UserEmail);
        goToFav = view.findViewById(R.id.goToFav);
        goToCal = view.findViewById(R.id.goToCal);
        logout = view.findViewById(R.id.logout);
        back = view.findViewById(R.id.backIV) ;
        backup=view.findViewById(R.id.backup);
        // Example: Set user details (replace with actual data retrieval logic)
        tvUserName.setText(presenter.getName());
        tvUserEmail.setText(presenter.getEmail());

        // Set click listeners
        goToFav.setOnClickListener(v -> navigateToFavorites());
        goToCal.setOnClickListener(v -> navigateToMealPlan());
        back.setOnClickListener(v -> navigateToHome() );
        logout.setOnClickListener(v -> presenter.logout(this.getContext()));
        backup.setOnClickListener(v->presenter.getAllMeals());
    }
    private void navigateToFavorites() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_favouritFragment);
    }
    private void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_homeFragment);
    }

    private void navigateToMealPlan() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_calenderFragment);
    }
    @Override
    public void logoutUser() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_splashFragment);
    }

    @Override
    public void showError(String message) {

    }
}