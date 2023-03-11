package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe_research.Adapters.RandomRecipeAdapter;

import com.example.recipe_research.Listeners.RecipeClickListener;
import com.example.recipe_research.Models.Recipe;
import com.example.recipe_research.db.RecipeDao;
import com.example.recipe_research.db.RecipeDatabase;
import com.example.recipe_research.db.RecipeEntity;

import java.util.ArrayList;
import java.util.List;


public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    private Button delete;
    private AlertDialog.Builder builder;
    private RecipeDatabase db;
    private RecipeDao recipeDao;
    private boolean flag;

    // Creates a new ContentView for the activity_history activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        assign();
    }

    // refreshes the activity to show the latest data provided by the database
    @SuppressLint("SetTextI18n")
    public void assign() {
        TextView databaseCount;
        RandomRecipeAdapter recipeAdapter;
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerDatabase);
        databaseCount = findViewById(R.id.databaseCount);
        delete = findViewById(R.id.deleteAll);

        delete.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);

        builder = new AlertDialog.Builder(this);
        recipeAdapter = new RandomRecipeAdapter(DatabaseActivity.this, fetchData(), recipeClickListener);

        recyclerView.setLayoutManager(new GridLayoutManager(DatabaseActivity.this, 1));
        recyclerView.setAdapter(recipeAdapter);

        databaseCount.setText(getString(R.string.you_have) + " " + recipeDao.getCount() + getString(R.string.bookmarks));
    }

    // Assigns OnClick Listener
    private final RecipeClickListener recipeClickListener = id -> startActivity(new Intent(DatabaseActivity.this, DatabaseDetailsActivity.class)
            .putExtra(getString(R.string.id), id));


    // Deletes all bookmarks
    private List<Recipe> fetchData() {

        List<Recipe> recipe = new ArrayList<>();

        Recipe meal;
        db = RecipeDatabase.getSingletonInstance(this);
        recipeDao = db.recipeDao();

        for (int i = 0; i < recipeDao.getCount(); i++) {
            RecipeEntity[] recipeEntity = recipeDao.getAllRecipes();
            meal = toRecipe(recipeEntity[i]);
            recipe.add(meal);
        }
        return recipe;
    }

    // Converts the RecipeEntity to a public Recipe
    public Recipe toRecipe(RecipeEntity re) {

        Recipe recipe = new Recipe();
        recipe.title = re.title;
        recipe.image = re.image;
        recipe.servings = re.servings;
        recipe.readyInMinutes = re.readyInMinutes;
        recipe.vegan = re.vegan;
        recipe.vegetarian = re.vegetarian;
        recipe.glutenFree = re.glutenFree;
        recipe.dairyFree = re.dairyFree;
        recipe.id = re.id;

        return recipe;
    }

    // Starts Builder to delete all bookmarks
    @Override
    public void onClick(View v) {
        if (v == delete) {
            builder.setTitle(getString(R.string.builder_title));
            builder.setMessage(getString(R.string.bookmark_msg_all));
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.yesButton), (dialogInterface, i) -> delete());
            builder.setNegativeButton(getString(R.string.noButton), (dialogInterface, i) -> dialogInterface.cancel());
            builder.show();
        }
    }

    //Deletes all bookmarks
    public void delete() {
        db = RecipeDatabase.getSingletonInstance(this);
        recipeDao = db.recipeDao();
        recipeDao.deleteAll();
        Toast.makeText(this, R.string.all_bookmarks_del, Toast.LENGTH_SHORT).show();
        assign();
    }

    // Refreshes the activity
    @Override
    protected void onResume() {
        super.onResume();

        if (flag) {
            flag = false;
            this.recreate();
        } else {
            flag = true;
        }
    }
}
