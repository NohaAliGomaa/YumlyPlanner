package com.example.yumlyplanner.loginfragment.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.loginfragment.presenter.LoginPresenter;
import com.example.yumlyplanner.loginfragment.presenter.LoginPresenterImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginFragment extends Fragment  implements LoginViewInterface{
    private Button login,signUp;
    private TextInputEditText email;
    private TextInputEditText password;
    private LoginPresenter presenter;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       presenter=new LoginPresenterImpl(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignInButton signInButton = view.findViewById(R.id.btnGoogleSignIn);
        login=view.findViewById(R.id.btn_login);
        signUp=view.findViewById(R.id.signUpButton);
        email=view.findViewById(R.id.et_Email);
        password=view.findViewById(R.id.et_Password);
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setText(" Login  with  Google");
                break;
            }
        }
        signInButton.setOnClickListener(v -> signInWithGoogle());
        login.setOnClickListener(v -> presenter.handleEmailLogin(email.getText().toString(), password.getText().toString()));
        signUp.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));
    }
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.handleGoogleLogin(account.getIdToken());
            } catch (ApiException e) {
                showGoogleSignInError(e.getLocalizedMessage());
            }
        }
    }
    @Override
    public void showLoginSuccess() {
        Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(getContext(), "Authentication failed: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void showGoogleSignInError(String message) {
        Toast.makeText(getContext(), "Google sign-in failed: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGoogleSignSuccess(String message) {
        Toast.makeText(getContext(), "Google sign-in Success: " + message, Toast.LENGTH_SHORT).show();
    }
}