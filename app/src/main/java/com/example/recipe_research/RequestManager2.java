package com.example.recipe_research;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.recipe_research.Listeners.NutritionByIdListener;
import com.example.recipe_research.Listeners.RandomRecipeResponseListener;
import com.example.recipe_research.Listeners.RecipeDetailsListener;
import com.example.recipe_research.Models.NutritionByIdResponse;
import com.example.recipe_research.Models.RandomRecipeApiResponse;
import com.example.recipe_research.Models.RecipeDetailsResponse;

import org.jsoup.Jsoup;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager2 extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
