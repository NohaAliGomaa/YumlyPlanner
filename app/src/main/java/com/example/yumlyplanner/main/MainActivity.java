package com.example.yumlyplanner.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.model.authentication.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.calenderFragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        boolean isGuest = SessionManager.getInstance(MainActivity.this).isGuestMode();

//        // Disable FAB if guest
//        if (isGuest) {
//            fab.setEnabled(false);
////            fab.setAlpha(0.5f); // Make it visually disabled
//        }

        fab.setOnClickListener(v -> {
            if (isGuest) {
                Toast.makeText(MainActivity.this, "Sign in to save and plan meals!", Toast.LENGTH_LONG).show();
            } else {
                navController.navigate(R.id.calenderFragment);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.homeFragment) {
                    navController.navigate(R.id.homeFragment);
                    return true;
                } else if (itemId == R.id.searchFragment) {
                    navController.navigate(R.id.searchFragment);
                    return true;
                } else if (itemId == R.id.favouritFragment) {
                    if (isGuest) {
                        Toast.makeText(MainActivity.this, "Sign in to access favorites!", Toast.LENGTH_LONG).show();
                        return false; // Prevent navigation
                    } else {
                        navController.navigate(R.id.favouritFragment);
                        return true;
                    }
                } else if (itemId == R.id.profileFragment) {
                    navController.navigate(R.id.profileFragment);
                    return true;
                }
                return false;
            }
        });

        // Disable "Favorites" tab if user is a guest
        if (isGuest) {
            bottomNavigationView.getMenu().findItem(R.id.favouritFragment).setEnabled(false);
        }
    }
}
