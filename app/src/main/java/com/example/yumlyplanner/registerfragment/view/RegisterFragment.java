package com.example.yumlyplanner.registerfragment.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.homefragment.view.HomeFragment;
import com.example.yumlyplanner.registerfragment.presenter.RegisterPresenter;
import com.example.yumlyplanner.registerfragment.presenter.RegisterPresenterImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;


public class RegisterFragment extends Fragment implements  RegisterView{

    private Button login,signUp;
    private TextInputEditText email,userName;
    private TextInputEditText password,confirmPassword;
    private GoogleSignInClient googleSignInClient;
    private RegisterPresenter presenter;
    private static final int RC_SIGN_IN = 9001;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        presenter = new RegisterPresenterImpl(this );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignInButton signInButton=view.findViewById(R.id.btnGoogleSignIn);
        login=view.findViewById(R.id.registerButton);
        signUp=view.findViewById(R.id.loginBtn);
        userName=view.findViewById(R.id.et_Name);
        email=view.findViewById(R.id.et_Email);
        password=view.findViewById(R.id.et_Password);
        confirmPassword=view.findViewById(R.id.et_Password_confirm);
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setText(" Sign Up Google");
                break;
            }
        }


        signUp.setOnClickListener(v -> {
            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                showError("Please enter email and password");
                return;
            }

            Toast.makeText(getContext(), "Sign-Up Clicked", Toast.LENGTH_SHORT).show(); // Debugging
            presenter.registerUser(userEmail, userPassword);

        });
        signInButton.setOnClickListener(v->signUpWithGoogle());
    }
    public void signUpWithGoogle() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }


    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                presenter.signInWithGoogle(credential);
            } catch (ApiException e) {
                showError("Google sign-in failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_homeFragment);
//        requireActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, new HomeFragment())
//                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.calenderFragment).setVisibility(View.GONE);
    }
}