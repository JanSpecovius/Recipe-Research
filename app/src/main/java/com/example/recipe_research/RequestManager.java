package com.example.recipe_research;

import android.content.Context;

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

public class RequestManager {
    private final Context context;
    private final Retrofit retrofit;

    //request manager constructor for retrofit and logging interceptor
    public RequestManager(Context context) {
        this.context = context;

        //Logging Interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        
        //written in constructor because of logging Interceptor
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .build();
    }

    //Interface for getting random recipes from API
    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_Key), "10", tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {

            //if response is successful, return the response body and message
            @Override
            public void onResponse(@NonNull Call<RandomRecipeApiResponse> call, @NonNull Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            //if response is not successful, return the error message
            @Override
            public void onFailure(@NonNull Call<RandomRecipeApiResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    //Interface for getting nutrition information from API
    public void getRecipeDetails(RecipeDetailsListener listener, int id) {
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_Key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {

            //if response is successful, return the response body and message
            @Override
            public void onResponse(@NonNull Call<RecipeDetailsResponse> call, @NonNull Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                RecipeDetailsResponse responseBody = response.body();
                if (responseBody != null && responseBody.getSummary() != null) {
                    String summary = Jsoup.parse(responseBody.getSummary()).text();
                    responseBody.setSummary(summary);
                }
                listener.didFetch(responseBody, response.message());
            }

            //if response is not successful, return the error message
            @Override
            public void onFailure(@NonNull Call<RecipeDetailsResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    //Interface for getting nutrition information from API
    public void getNutritionById(NutritionByIdListener listener, int id) {
        Call<NutritionByIdResponse> call = retrofit.create(NutritionService.class).getNutritionById(id, context.getString(R.string.api_Key));
        call.enqueue(new Callback<NutritionByIdResponse>() {

            //if response is successful, return the response body and message
            @Override
            public void onResponse(@NonNull Call<NutritionByIdResponse> call, @NonNull Response<NutritionByIdResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    listener.onNutritionByIdReceived(response.body(), response.message());
                } else {
                    listener.onNutritionByIdError("Error retrieving nutrition by id");
                }
            }

            //if response is not successful, return the error message
            @Override
            public void onFailure(@NonNull Call<NutritionByIdResponse> call, @NonNull Throwable t) {
                listener.onNutritionByIdError(t.getMessage());
            }
        });
    }

    //Interface for getting random recipes from API
    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    //Interface for getting recipe details from API
    private interface CallRecipeDetails {
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    //Interface for getting nutrition information from API
    public interface NutritionService {
        @GET("recipes/{id}/nutritionWidget.json")
        Call<NutritionByIdResponse> getNutritionById(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}