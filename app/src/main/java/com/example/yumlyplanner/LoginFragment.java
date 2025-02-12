package com.example.yumlyplanner;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginFragment extends Fragment {
    private Button login,signUp;
    private TextInputEditText email;
    private TextInputEditText password;
    private FirebaseAuth mAuth;
    private View currentView;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentView=view;
        SignInButton signInButton = view.findViewById(R.id.btnGoogleSignIn);
        login=view.findViewById(R.id.btn_login);
        signUp=view.findViewById(R.id.signUpButton);
        email=view.findViewById(R.id.et_Email);
        password=view.findViewById(R.id.et_Password);
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setText("Login with Google");
                break;
            }
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Get this from Firebase
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        signInButton.setOnClickListener(v -> signInWithGoogle());
        login.setOnClickListener(v -> attemptLogin());
        signUp.setOnClickListener(v-> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));
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
                firebaseAuthWithGoogle(account.getIdToken());
                Navigation.findNavController(currentView).navigate(R.id.action_loginFragment_to_homeFragment);
            } catch (ApiException e) {
                Log.w("GoogleSignIn", "Google sign-in failed", e);
                Toast.makeText(getContext(), "Google sign-in failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(getContext(), "Signed in as: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(currentView).navigate(R.id.action_loginFragment_to_homeFragment);
                    } else {
                        Toast.makeText(getContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void attemptLogin() {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        if (!isValidEmail(emailInput)) {
            email.setError("Invalid email format");
            return;
        }

        if (TextUtils.isEmpty(passwordInput)) {
            password.setError("Password cannot be empty");
            return;
        }

        // Firebase Email Authentication
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null ) {
                            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(currentView).navigate(R.id.action_loginFragment_to_homeFragment);
                        } else {
                            Toast.makeText(getContext(), "Please verify your email!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}