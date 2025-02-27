package com.example.yumlyplanner.splashfragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yumlyplanner.R;
import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.splashfragment.presenter.SplashPresenterIml;

public class SplashFragment extends Fragment implements SplashView {
    private LottieAnimationView animationView;
    private SplashPresenterIml presenterIml;

    public SplashFragment() {
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
        return inflater.inflate(R.layout.splah_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenterIml=new SplashPresenterIml(this, MealLocalDataSource.getInstance(this.getContext()),this.getContext());
        animationView = view.findViewById(R.id.animationView);
        animationView.playAnimation();
        Handler handler = new Handler();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded()) {
                presenterIml.isLogged();
//                Navigation.findNavController(this.getView()).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 4000);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animationView.cancelAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.calenderFragment).setVisibility(View.GONE);
    }

    @Override
    public void checkLoginStatus() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }

    @Override
    public void navagiteToHome() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_splashFragment_to_homeFragment);
    }
}