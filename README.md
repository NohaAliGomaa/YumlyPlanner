# Food Planner Application

## Overview

Food Planner is a powerful Android application designed to assist users in organizing their weekly meals efficiently. 
It enables users to explore meal categories, search for specific meals, receive meal suggestions,
and save favorites for offline access. The app also includes meal planning, 
data backup, and synchronization through Firebase authentication.

## Features

- **Meal of the Day**: Displays a random meal suggestion for inspiration.
- **Advanced Meal Search**: Find meals by country, ingredient, or category.
- **Browse by Categories & Countries**: Easily navigate meals based on different categories and cuisines.
- **Favorites Management**: Save meals as favorites for easy offline access (stored locally using Room database).
- **Weekly Meal Planning**: Plan and organize meals for the entire week.
- **Offline Access**: View favorite meals and weekly plans without an internet connection.
- **User Authentication**:
  - Supports email/password login and social logins (Google, Facebook, Twitter) via Firebase Authentication.
  - Persistent user sessions managed via SharedPreferences.
  - Guest mode allowing limited functionality without login.
- **Detailed Meal Information**:
  - Displays meal name, image, country of origin, ingredients (with images where available), preparation steps, and embedded video tutorial.
  - Ability to add or remove meals from favorites.
- **Animated Splash Screen**: Engaging splash screen animation using Lottie.


## Technical Specifications

- **Architecture**: Built using the MVP (Model-View-Presenter) pattern.
- **Single Activity Structure**: Designed as a single-activity application with multiple fragments for navigation.
- **Networking**: Utilizes Retrofit for fetching data from [TheMealDB API](https://themealdb.com/api.php).
- **Asynchronous Operations**: Implements RxJava for efficient background processing.
- **Local Storage**: Room Database is used for caching meal data and managing user favorites.
- **Cloud Backup & Sync**: Firebase Firestore ensures data synchronization across multiple devices.

## Installation & Setup

1. Open the project in Android Studio.
2. Sync Gradle dependencies.
3. Configure Firebase by adding `google-services.json` to the `app/` directory.
4. Build and run the application on an emulator or a physical device.

## Required Dependencies

- Retrofit
- RxJava
- Room Database
- Firebase Authentication & Firestore
- Lottie for animations
- Glide for image loading

## Contribution Guidelines

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Write clear, concise commit messages.
4. Push your branch and submit a pull request.


