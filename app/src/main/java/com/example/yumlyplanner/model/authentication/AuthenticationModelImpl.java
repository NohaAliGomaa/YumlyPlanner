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
import com.google.firebase.auth.UserProfileChangeRequest;
import java.util.List;

public class AuthenticationModelImpl implements AuthenticationModel {
    private final FirebaseAuth mAuth;
    private final BackupManager backupManager;
    private final SessionManager sessionManager;
    private FirebaseUser user;
    private String userId, userName, userEmail;
    private  MealLocalDataSource localDataSource;
    private static final String TAG = "AuthenticationModelImpl";

    public AuthenticationModelImpl(MealLocalDataSource localDataSource, Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.backupManager = new BackupManager(localDataSource);
        this.localDataSource=localDataSource;
        this.sessionManager = SessionManager.getInstance(context);

        if (sessionManager.isGuestMode()) {
            userId = "GUEST";
            userName = "Guest User";
            userEmail = "N/A";
            Log.i(TAG, "Guest Mode: userId set to GUEST");
        } else {
            user = mAuth.getCurrentUser();
            if (user != null) {
                userId = user.getUid();
                userName = user.getDisplayName() != null ? user.getDisplayName() : "N/A";
                userEmail = user.getEmail() != null ? user.getEmail() : "N/A";
                Log.i(TAG, "User Logged In: " + userId);
            } else {
                userId = null;
                userName = "N/A";
                userEmail = "N/A";
                Log.e(TAG, "No authenticated user found!");
            }
        }
    }

    public void loginAsGuest(Context context, LoginCallBack callback) {
        sessionManager.setGuestMode(true);
        userId = "GUEST";
        userName = "Guest User";
        userEmail = "N/A";
        callback.onSuccess(null);
        Log.i(TAG, "User logged in as Guest");
    }

    @Override
    public void loginWithEmail(String email, String password, LoginCallBack callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            userId = user.getUid();
                            userName = user.getDisplayName() != null ? user.getDisplayName() : "Unknown User";
                            userEmail = user.getEmail() != null ? user.getEmail() : "No Email";

                            sessionManager.saveUserSession(userId, userName, userEmail);
                            restorData(userId);
                            callback.onSuccess(user);
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
                        user = mAuth.getCurrentUser();
                        if (user != null) {
                            userId = user.getUid();
                            userName = user.getDisplayName() != null ? user.getDisplayName() : "Unknown User";
                            userEmail = user.getEmail() != null ? user.getEmail() : "No Email";

                            sessionManager.saveUserSession(userId, userName, userEmail);
                            restorData(userId);
                            callback.onSuccess(user);
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }
    @Override
    public void signInWithGoogle(AuthCredential credential, RegisterCallBack listener) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();
                        if (user != null) {
                            userId = user.getUid();
                            userName = user.getDisplayName() != null ? user.getDisplayName() : "Unknown User";
                            userEmail = user.getEmail() != null ? user.getEmail() : "No Email";

                            sessionManager.saveUserSession(userId, userName, userEmail);}
                        listener.showOnRegisterSuccess("register with google is done");
                    } else {
                        listener.showOnRegisterError(task.getException().getMessage());
                    }
                });


    }

    @Override
    public void authenticateWithFirebase(String idToken, RegisterCallBack callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
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
                        user = mAuth.getCurrentUser();
                        if (user != null) {
                            userId = user.getUid();
                            userEmail = user.getEmail() != null ? user.getEmail() : "No Email";

                            // Set default display name
                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                            .setDisplayName("New User")
                                            .build())
                                    .addOnCompleteListener(updateTask -> {
                                        userName = user.getDisplayName();
                                        sessionManager.saveUserSession(userId, userName, userEmail);
                                        listener.showOnRegisterSuccess("Registration successful");
                                    });
                        } else {
                            listener.showOnRegisterError("User registration failed: FirebaseUser is null.");
                        }
                    } else {
                        listener.showOnRegisterError(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void logout(Context context) {
        Log.i(TAG, "Logging out user");
        if (sessionManager.isGuestMode()) {
            sessionManager.setGuestMode(false);
            Log.i(TAG, "Guest Mode ended.");
        } else {
            mAuth.signOut();
            userId = null;
            user = null;
            GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                    .revokeAccess()
                    .addOnCompleteListener(task -> Log.i(TAG, "Google Sign-Out successful"));
        }
        sessionManager.logout();
        new Thread(() -> {
            localDataSource.deleteAll();
            Log.i(TAG, "Room database cleared successfully.");
        }).start();
        Log.i(TAG, "Session cleared successfully.");
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

    public boolean checkLoginStatus() {
        return sessionManager.isLoggedIn();
    }

    public String getName() {
        return sessionManager.getUserName();
    }

    public String getEmail() {
        return sessionManager.getUserEmail();
    }
}
