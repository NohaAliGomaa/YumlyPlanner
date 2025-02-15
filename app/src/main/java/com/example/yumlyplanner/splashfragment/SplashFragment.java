package com.example.yumlyplanner.splashfragment;

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

public class SplashFragment extends Fragment {
    LottieAnimationView animationView;

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
        animationView = view.findViewById(R.id.animationView);
        animationView.playAnimation();
        Handler handler = new Handler();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded()) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 4000);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animationView.cancelAnimation();
    }
}