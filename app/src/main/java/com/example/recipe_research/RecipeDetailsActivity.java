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
    private TextView textView_meal_name, textView_meal_source, textView_meal_summary, textView_meal_nutrition, textView_meal_ingredients;
    private RequestManager manager;
    private LoadingDialog loadingDialog;
    private ImageView share, bookmark, imageView_meal_name;
    private AlertDialog.Builder builder;

    private String title, sourceName, summary, image, url, calories, carbs, fat, protein, badName, badAmount, ingredients;
    private String [] ingrArray;
    private int amount, readyInTime, servings, spoonacular_id;
    private boolean _glutenfree,_vegetarian,_vegan,_dairyFree,_flag;
    private RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        assign();

    }


    private void assign() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        textView_meal_nutrition = findViewById(R.id.textView_meal_nutrition);
        imageView_meal_name = findViewById(R.id.imageView_meal_name);
        textView_meal_ingredients = findViewById(R.id.textView_meal_ingredients);
        share = findViewById(R.id.share);
        bookmark = findViewById(R.id.bookmark);

        share.setOnClickListener(this);
        bookmark.setOnClickListener(this);

        _flag = false;

        recipeDao = RecipeDatabase.getSingletonInstance(this).recipeDao();
        id = Integer.parseInt(getIntent().getStringExtra(getString(R.string.id)));

        manager = new RequestManager(this);
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
            spoonacular_id = response.id;
            readyInTime = response.readyInMinutes;
            servings = response.servings;


            _glutenfree = response.glutenFree;
            _vegetarian = response.vegetarian;
            _vegan = response.vegan;
            _dairyFree = response.dairyFree;

            if (isInDatabase(spoonacular_id)) {
                bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);
                _flag = true;
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
        if (v == share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.meal_found_mail) + url);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));

        } else if (v == bookmark) {


            if (_flag) {


                builder = new AlertDialog.Builder(this);

                builder.setTitle("Warning!!!");
                builder.setMessage("Do you really want to delete this bookmark?");
                builder.setCancelable(true);
                builder.setPositiveButton(getString(R.string.yesButton), (dialogInterface, i) -> deleteRow(spoonacular_id));
                builder.setNegativeButton(getString(R.string.noButton), (dialogInterface, i) -> dialogInterface.cancel());
                builder.show();

            }else {
                bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);

                insertRow();
                _flag = true;
            }

        }
    }


    public void assignRecipeDetail() {
        textView_meal_name.setText(title);
        textView_meal_source.setText(sourceName);
        textView_meal_summary.setText(summary);
        Picasso.get().load(image).into(imageView_meal_name);

        StringBuilder sb = new StringBuilder();

        int counter = 0;

        while (amount > counter) {

            sb.append(ingrArray[counter]).append("\n");
            counter++;
        }

        ingredients = sb.toString();
        // Set the string builder text to the text view
        textView_meal_ingredients.setText(ingredients);

    }

    public void assignNutritonDetail() {


        StringBuilder sb = new StringBuilder();

        // Append the nutrition values to the string builder
        sb.append(getString(R.string.calories)).append(calories).append("\n");
        sb.append(getString(R.string.carbs)).append(carbs).append("\n");
        sb.append(getString(R.string.fat)).append(fat).append("\n");
        sb.append(getString(R.string.protein)).append(protein).append("\n");
        sb.append(badName + ": ").append(badAmount).append("\n");


        // Set the string builder text to the text view
        textView_meal_nutrition.setText(sb.toString());

    }


    public void insertRow() {
        RecipeEntity entity = new RecipeEntity();
        entity.apiID = spoonacular_id;
        entity.title = title;
        entity.dairyFree = _dairyFree;
        entity.glutenFree = _glutenfree;
        entity.vegan = _vegan;
        entity.vegetarian = _vegetarian;
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
        _flag = false;

    }

    public boolean isInDatabase(int id) {
        return recipeDao.getRecipeByApiId(id) != null;
    }
}