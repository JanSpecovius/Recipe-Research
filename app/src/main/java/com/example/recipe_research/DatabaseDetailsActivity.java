package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.recipe_research.db.RecipeDao;
import com.example.recipe_research.db.RecipeDatabase;
import com.example.recipe_research.db.RecipeEntity;
import com.squareup.picasso.Picasso;

public class DatabaseDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewMealName;
    private TextView textViewMealSource;
    private TextView textViewMealSummary;
    private TextView textViewMealNutrition;
    private TextView textViewMealIngredients;
    private ImageView share;
    private ImageView imageViewMealName;
    private ImageView bookmark;
    private AlertDialog.Builder builder;
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
    private int id;
    private RecipeDao recipeDao;

    // Creates a new ContentView for the activity_history activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_details);
        assign();
        setDataFromDatabase(id);
        assignRecipeDetail();
        assignNutritonDetail();
    }

    // Assigning views, listeners and database
    @SuppressLint("SetTextI18n")
    private void assign() {
        TextView textViewCreateTime;
        textViewMealName = findViewById(R.id.textView_db_meal_name);
        textViewMealSource = findViewById(R.id.textView_db_meal_source);
        textViewMealSummary = findViewById(R.id.textView_db_meal_summary);
        textViewMealNutrition = findViewById(R.id.textView_db_meal_nutrition);
        imageViewMealName = findViewById(R.id.imageView_db_meal_name);
        textViewMealIngredients = findViewById(R.id.textView_db_meal_ingredients);
        textViewCreateTime = findViewById(R.id.textView_db_meal_createTime);
        share = findViewById(R.id.imageView_db_share);
        bookmark = findViewById(R.id.imageView_db_bookmark);

        recipeDao = RecipeDatabase.getSingletonInstance(this).recipeDao();

        builder = new AlertDialog.Builder(this);
        id = Integer.parseInt(getIntent().getStringExtra(getString(R.string.id)));

        textViewCreateTime.setText(getString(R.string.db_timestamp) + " " + recipeDao.getDateById(id).toString());
        bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);

        textViewMealSource.setOnClickListener(this);
        share.setOnClickListener(this);
        bookmark.setOnClickListener(this);
    }

    // Set data from database
    public void setDataFromDatabase(int id) {
        RecipeEntity recipeEntity = recipeDao.getRecipeById(id);
        title = recipeEntity.title;
        sourceName = recipeEntity.sourceName;
        summary = recipeEntity.summary;
        image = recipeEntity.image;
        url = recipeEntity.url;
        calories = recipeEntity.calories;
        carbs = recipeEntity.carbs;
        fat = recipeEntity.fat;
        protein = recipeEntity.protein;
        badName = recipeEntity.badName;
        badAmount = recipeEntity.badAmount;
        ingredients = recipeEntity.ingredients;
    }

    // On click listener for share and bookmark buttons and also for the source text_view to open the recipe in browser
    @Override
    public void onClick(View v) {
        if (v == share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.meal_found_mail) + url);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));
        } else if (v == bookmark) {
            builder.setTitle(R.string.builder_title);
            builder.setMessage(getString(R.string.bookmark_msg));
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.yesButton), (dialogInterface, i) -> deleteFromDatabase());
            builder.setNegativeButton(getString(R.string.noButton), (dialogInterface, i) -> dialogInterface.cancel());
            builder.show();
        } else if (v == textViewMealSource) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    //  Assigning recipe details
    public void assignRecipeDetail() {
        textViewMealName.setText(title);
        textViewMealSource.setText(sourceName);
        textViewMealSummary.setText(summary);
        textViewMealIngredients.setText(ingredients);
        Picasso.get().load(image).into(imageViewMealName);
    }

    //  Assigning nutrition details
    public void assignNutritonDetail() {
        // Append the nutrition values to the string builder
        String sb = getString(R.string.calories) + " " + calories + "\n" +
                getString(R.string.carbs) + " " + carbs + "\n" +
                getString(R.string.fat) + " " + fat + "\n" +
                getString(R.string.protein) + " " + protein + "\n" +
                badName + getString(R.string.dp) + " " + badAmount + "\n";

        // Set the string builder text to the text view
        textViewMealNutrition.setText(sb);
    }

    //  Delete from database
    public void deleteFromDatabase() {
        recipeDao.deleteById(id);
        finish();
    }
}