package com.example.yumlyplanner.registerfragment.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.registerfragment.presenter.RegisterPresenter;
import com.example.yumlyplanner.registerfragment.presenter.RegisterPresenterImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;


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
        login=view.findViewById(R.id.loginBtn);
        signUp=view.findViewById(R.id.registerButton);
        userName=view.findViewById(R.id.et_Name);
        email=view.findViewById(R.id.et_Email);
        password=view.findViewById(R.id.et_Password);
        confirmPassword=view.findViewById(R.id.et_Password_confirm);
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setText(" Login  with  Google");
                break;
            }
        }
        signInButton.setOnClickListener(v->signUpWithGoogle(this));
    }
    public void signUpWithGoogle(Fragment fragment) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        fragment.startActivityForResult(signInIntent, 9002);
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
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_homeFragment);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            presenter.handleGoogleSignUpResult(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.calenderFragment).setVisibility(View.GONE);
    }
}