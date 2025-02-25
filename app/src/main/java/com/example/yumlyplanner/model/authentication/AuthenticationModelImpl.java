package com.example.yumlyplanner.model.authentication;

import android.content.Context;
import android.util.Log;

import com.example.yumlyplanner.model.local.MealLocalDataSource;
import com.example.yumlyplanner.model.network.LoginCallBack;
import com.example.yumlyplanner.model.network.RegisterCallBack;
import com.example.yumlyplanner.model.pojo.Meal;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

public class AuthenticationModelImpl implements AuthenticationModel {
    private final FirebaseAuth mAuth;
    private  BackupManager backupManager ;
    private  String userId;
    FirebaseUser user;

    private static final String TAG = "AuthenticationModelImpl";

    public AuthenticationModelImpl(MealLocalDataSource localDataSource, Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        backupManager = new BackupManager(localDataSource);

        if (SessionManager.getInstance(context).isGuestMode()) {
            userId = "GUEST";  // Assign a dummy ID for guest users
            Log.i(TAG, "User is in Guest Mode");
        } else {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                userId = user.getUid();
            } else {
                userId = null;
                Log.e(TAG, "No authenticated user found!");
            }
        }
    }
    public void loginAsGuest(Context context, LoginCallBack callback) {
        SessionManager.getInstance(context).setGuestMode(true);
        userId = "GUEST"; // Assign a dummy ID
        callback.onSuccess(null); // No FirebaseUser object needed for guest mode
        Log.i(TAG, "User logged in as Guest");
    }


    @Override
    public void loginWithEmail(String email, String password, LoginCallBack callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            callback.onSuccess(user);
                            userId=user.getUid();
                            restorData(userId);
                        } else {
                            callback.onFailure("Please verify your email!");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void loginWithGoogle(String idToken, LoginCallBack callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSuccess(user);
                        restorData(user.getUid());
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void authenticateWithFirebase(String idToken, RegisterCallBack callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                            callback.showOnRegisterSuccess("Registration successful!");
                        } else {
                            callback.showOnRegisterError("User already exists. Please log in instead.");
                        }
                    } else {
                        callback.showOnRegisterError("Registration failed: " + task.getException().getMessage());
                    }
                });
    }


    @Override
    public void registerUser(String email, String password, RegisterCallBack listener) {
       mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.showOnRegisterSuccess("the register is done");
                    } else {
                        listener.showOnRegisterError(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signInWithGoogle(AuthCredential credential, RegisterCallBack listener) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.showOnRegisterSuccess("register with google is done");
                    } else {
                        listener.showOnRegisterError(task.getException().getMessage());
                    }
                });

    }

    @Override
    public void logout(Context context) {
        Log.i(TAG, "logout: Logging out user");

        if (SessionManager.getInstance(context).isGuestMode()) {
            SessionManager.getInstance(context).setGuestMode(false);
            Log.i(TAG, "Guest Mode ended.");
        } else {
            mAuth.signOut();
            GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                    .signOut()
                    .addOnCompleteListener(task -> Log.i(TAG, "logout: Google Sign-Out successful"));
        }
    }

    @Override
    public void backup(List<Meal> mealList) {
        if (userId != null) {
            backupManager.backupMeals(mealList, userId);
        } else {
            Log.e(TAG, "Backup failed: User ID is null. User may not be logged in.");
        }
    }

    @Override
    public void restorData(String userId) {
        backupManager.restoreMeals(userId);
    }

}
