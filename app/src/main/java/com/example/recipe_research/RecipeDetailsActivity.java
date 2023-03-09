package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe_research.Listeners.NutritionByIdListener;
import com.example.recipe_research.Listeners.RecipeDetailsListener;
import com.example.recipe_research.Models.NutritionByIdResponse;
import com.example.recipe_research.Models.RecipeDetailsResponse;
import com.example.recipe_research.db.RecipeDao;
import com.example.recipe_research.db.RecipeDatabase;
import com.example.recipe_research.db.RecipeEntity;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class RecipeDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private int id;
    private TextView textViewMealName;
    private TextView textViewMealSource;
    private TextView textViewMealSummary;
    private TextView textViewMealNutrition;
    private TextView textViewMealIngredients;
    private LoadingDialog loadingDialog;
    private ImageView share;
    private ImageView bookmark;
    private ImageView imageViewMealName;


    private String title;
    private String sourceName;
    private String summary;
    private String image;
    private String url;
    private String calories;
    private String carbs;
    private String fat;
    private String protein;
    private String badName;
    private String badAmount;
    private String ingredients;
    private String [] ingrArray;
    private int amount;
    private int readyInTime;
    private int servings;
    private int spoonacularId;
    private boolean glutenfree;
    private boolean vegetarian;
    private boolean vegan;
    private boolean dairyFree;
    private boolean flag;
    private RecipeDao recipeDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        assign();

    }


    private void assign() {
        textViewMealName = findViewById(R.id.textView_meal_name);
        textViewMealSource = findViewById(R.id.textView_meal_source);
        textViewMealSummary = findViewById(R.id.textView_meal_summary);
        textViewMealNutrition = findViewById(R.id.textView_meal_nutrition);
        imageViewMealName = findViewById(R.id.imageView_meal_name);
        textViewMealIngredients = findViewById(R.id.textView_meal_ingredients);
        share = findViewById(R.id.share);
        bookmark = findViewById(R.id.bookmark);

        share.setOnClickListener(this);
        bookmark.setOnClickListener(this);

        flag = false;

        recipeDao = RecipeDatabase.getSingletonInstance(this).recipeDao();
        id = Integer.parseInt(getIntent().getStringExtra(getString(R.string.id)));

        RequestManager manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getNutritionById(nutritionByIdListener, id);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showLoading();

    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            loadingDialog.disMiss();

            title = response.title;
            sourceName = response.sourceName;
            summary = response.summary;
            image = response.image;
            url = response.spoonacularSourceUrl;
            spoonacularId = response.id;
            readyInTime = response.readyInMinutes;
            servings = response.servings;


            glutenfree = response.glutenFree;
            vegetarian = response.vegetarian;
            vegan = response.vegan;
            dairyFree = response.dairyFree;

            if (isInDatabase(spoonacularId)) {
                bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);
                flag = true;
            }

            amount = response.getExtendedIngredients().size();
            int counter = 0;

            ingrArray = new String[amount];

            while (amount > counter) {

                ingrArray[counter] = String.valueOf(response.getExtendedIngredients().get(counter).original);
                counter++;
            }


            assignRecipeDetail();
        }


        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, "Detail:" + message, Toast.LENGTH_SHORT).show();
            loadingDialog.disMiss();
            finish();
        }
    };

    private final NutritionByIdListener nutritionByIdListener = new NutritionByIdListener() {
        @Override
        public void onNutritionByIdReceived(NutritionByIdResponse nutrition, String message) {


            //Get the nutrition values from the response object
            calories = nutrition.getCalories();
            carbs = nutrition.getCarbs();
            fat = nutrition.getFat();
            protein = nutrition.getProtein();
            badName = nutrition.getBad().get(4).title;
            badAmount = nutrition.getBad().get(4).amount;


            assignNutritonDetail();

        }

        @Override
        public void onNutritionByIdError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder;
        if (v == share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.meal_found_mail) + url);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));

        } else if (v == bookmark) {


            if (flag) {


                builder = new AlertDialog.Builder(this);

                builder.setTitle("Warning!!!");
                builder.setMessage("Do you really want to delete this bookmark?");
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yesButton), (dialogInterface, i) -> deleteRow(spoonacularId));
                builder.setNegativeButton(getString(R.string.noButton), (dialogInterface, i) -> dialogInterface.cancel());
                builder.show();

            }else {
                bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);

                insertRow();
                flag = true;
            }

        }
    }


    public void assignRecipeDetail() {
        textViewMealName.setText(title);
        textViewMealSource.setText(sourceName);
        textViewMealSummary.setText(summary);
        Picasso.get().load(image).into(imageViewMealName);

        StringBuilder sb = new StringBuilder();

        int counter = 0;

        while (amount > counter) {

            sb.append(ingrArray[counter]).append("\n");
            counter++;
        }

        ingredients = sb.toString();
        // Set the string builder text to the text view
        textViewMealIngredients.setText(ingredients);

    }

    public void assignNutritonDetail() {


        // Append the nutrition values to the string builder
        String sb = getString(R.string.calories) + calories + "\n" +
                getString(R.string.carbs) + carbs + "\n" +
                getString(R.string.fat) + fat + "\n" +
                getString(R.string.protein) + protein + "\n" +
                badName + ": " + badAmount + "\n";


        // Set the string builder text to the text view
        textViewMealNutrition.setText(sb);

    }


    public void insertRow() {
        RecipeEntity entity = new RecipeEntity();
        entity.apiID = spoonacularId;
        entity.title = title;
        entity.dairyFree = dairyFree;
        entity.glutenFree = glutenfree;
        entity.vegan = vegan;
        entity.vegetarian = vegetarian;
        entity.image = image;
        entity.sourceName = sourceName;
        entity.summary = summary;
        entity.url = url;
        entity.calories = calories;
        entity.carbs = carbs;
        entity.fat = fat;
        entity.protein = protein;
        entity.badName = badName;
        entity.badAmount = badAmount;
        entity.readyInMinutes = readyInTime;
        entity.servings = servings;


        entity.ingredients = ingredients;

        entity.date = new Date();

        recipeDao.insert(entity);
    }

    public void deleteRow(int id) {
        recipeDao.deleteByApiId(id);
        bookmark.setBackgroundResource(R.drawable.ic_bookmark_border);
        flag = false;

    }

    public boolean isInDatabase(int id) {
        return recipeDao.getRecipeByApiId(id) != null;
    }
}