plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")
   


}

android {
    namespace = "com.example.yumlyplanner"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.yumlyplanner"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation (libs.lottie)
    implementation (libs.com.google.firebase.firebase.auth)
    implementation (libs.play.services.auth.v2070)
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.firebase:firebase-auth:22.1.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")

    implementation("androidx.room:room-rxjava3:2.6.1")

    implementation ("com.jakewharton.rxbinding4:rxbinding:4.0.0")

    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore:24.9.1")

// Firebase Authentication (if needed)
    implementation ("com.google.firebase:firebase-auth:22.3.1")

// Room Database
    implementation ("androidx.room:room-runtime:2.5.2")
    annotationProcessor ("androidx.room:room-compiler:2.5.2")

// RxJava support for Room (if using RxJava)
    implementation ("androidx.room:room-rxjava3:2.5.2")

// WorkManager (if you want automatic scheduled backups)
    implementation ("androidx.work:work-runtime:2.8.1")

    implementation ("com.google.code.gson:gson:2.10.1")
    // Import the BoM for the Firebase platform
    implementation (platform("com.google.firebase:firebase-bom:32.0.0"))

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")



}
