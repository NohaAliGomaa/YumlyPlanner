package com.example.yumlyplanner.model.remot;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.yumlyplanner.model.MealResponse;
import com.example.yumlyplanner.model.NetworkCallBack;
import com.example.yumlyplanner.model.pojo.Meal;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemotDataSourceImpl {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static volatile Retrofit retrofit = null;
    private  static MealsRemotDataSourceImpl remotDataSource = null;
    MealsRemoteDataSource mealsRemoteDataSource;
    List<Meal> meals;

    private MealsRemotDataSourceImpl() {
        getClient();
        mealsRemoteDataSource = retrofit.create(MealsRemoteDataSource.class);
    }
    public static MealsRemotDataSourceImpl getInstance() {
        if (remotDataSource == null) {
          remotDataSource = new MealsRemotDataSourceImpl();
        }
        return remotDataSource;
    }
    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (MealsRemotDataSourceImpl.class) {
                if (retrofit == null) { // Double-check locking
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(getUnsafeOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) { }
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) { }
                        @Override
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Meal> getRandomMeal(NetworkCallBack networkCallBack){
        meals=new ArrayList<Meal>();
        Call<MealResponse> call = mealsRemoteDataSource.getRandomMeal();

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    meals=response.body().getMeals();
                    networkCallBack.onSuccessResult(response.body().getMeals());


                } else {
                    Log.i(TAG, "onResponse: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallBack.onFailurResult(t.getMessage());
            }
        });
        return meals;
    }
}

