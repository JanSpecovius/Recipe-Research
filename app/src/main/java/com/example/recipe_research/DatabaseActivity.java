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
    private boolean veganFlag;
    private boolean vegetarianFlag;
    private boolean glutenFreeFlag;
    private boolean dairyFreeFlag;

    private Button vegan;
    private Button vegetarian;
    private Button glutenFree;
    private Button dairyFree;
    private Button clearFilter;

    // Creates a new ContentView for the activity_history activity and gets the saved instance state for the Buttons, if they where clicked before
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        //sets the flag to the saved instance state
        if (savedInstanceState != null) {
            veganFlag = savedInstanceState.getBoolean("VeganFlag");
            vegetarianFlag = savedInstanceState.getBoolean("VegetarianFlag");
            glutenFreeFlag = savedInstanceState.getBoolean("GlutenFreeFlag");
            dairyFreeFlag = savedInstanceState.getBoolean("DairyFreeFlag");
        }

        assign();
    }
    //assigns the View to the Buttons and sets the OnClick Listener


    //assigns the missing View elements, sets the OnClick Listener, the Adapter for the RecyclerView and the TextView
    @SuppressLint("SetTextI18n")
    public void assign() {
        TextView databaseCount;
        RandomRecipeAdapter recipeAdapter;
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recyclerDatabase);
        databaseCount = findViewById(R.id.databaseCount);
        delete = findViewById(R.id.deleteAll);
        vegan = findViewById(R.id.veganFilter);
        vegetarian = findViewById(R.id.vegetarianFilter);
        glutenFree = findViewById(R.id.glutenFilter);
        dairyFree = findViewById(R.id.dairyFilter);
        clearFilter = findViewById(R.id.clearFilter);

        delete.setOnClickListener(this);
        vegan.setOnClickListener(this);
        vegetarian.setOnClickListener(this);
        glutenFree.setOnClickListener(this);
        dairyFree.setOnClickListener(this);
        clearFilter.setOnClickListener(this);

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
        if(glutenFreeFlag){
            for (int i = 0; i < recipeDao.getGlutenfreeCount(); i++) {
                RecipeEntity[] recipeEntity = recipeDao.getGlutenfreeRecipes();
                meal = toRecipe(recipeEntity[i]);
                recipe.add(meal);
            }
        } else if (veganFlag) {
            for (int i = 0; i < recipeDao.getVeganCount(); i++) {
                RecipeEntity[] recipeEntity = recipeDao.getVeganRecipes();
                meal = toRecipe(recipeEntity[i]);
                recipe.add(meal);
            }
        } else if (vegetarianFlag) {
            for (int i = 0; i < recipeDao.getVegetarianCount(); i++) {
                RecipeEntity[] recipeEntity = recipeDao.getVegetarianRecipes();
                meal = toRecipe(recipeEntity[i]);
                recipe.add(meal);
            }
        } else if (dairyFreeFlag) {
            for (int i = 0; i < recipeDao.getDairyfreeCount(); i++) {
                RecipeEntity[] recipeEntity = recipeDao.getDairyfreeRecipes();
                meal = toRecipe(recipeEntity[i]);
                recipe.add(meal);
            }
        } else {
            for (int i = 0; i < recipeDao.getCount(); i++) {
                RecipeEntity[] recipeEntity = recipeDao.getAllRecipes();
                meal = toRecipe(recipeEntity[i]);
                recipe.add(meal);
            }
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
        } else if (v == vegan) {
            dairyFreeFlag = false;
            glutenFreeFlag = false;
            vegetarianFlag = false;
            veganFlag = true;
            Toast.makeText(this, getString(R.string.filteredBy)+" "+getString(R.string.vegan_filter), Toast.LENGTH_SHORT).show();
            assign();

        } else if (v == vegetarian) {

            dairyFreeFlag = false;
            glutenFreeFlag = false;
            vegetarianFlag = true;
            veganFlag = false;
            Toast.makeText(this, getString(R.string.filteredBy)+" "+getString(R.string.vegetarian_filter), Toast.LENGTH_SHORT).show();
            assign();

        } else if (v == glutenFree) {

            dairyFreeFlag = false;
            glutenFreeFlag = true;
            vegetarianFlag = false;
            veganFlag = false;
            Toast.makeText(this, getString(R.string.filteredBy)+" "+getString(R.string.gluten_free_filter), Toast.LENGTH_SHORT).show();
            assign();

        } else if (v == dairyFree) {

            dairyFreeFlag = true;
            glutenFreeFlag = false;
            vegetarianFlag = false;
            veganFlag = false;
            Toast.makeText(this, getString(R.string.filteredBy)+" "+getString(R.string.dairy_free_filter), Toast.LENGTH_SHORT).show();
            assign();


        } else if (v == clearFilter) {
            dairyFreeFlag = false;
            glutenFreeFlag = false;
            vegetarianFlag = false;
            veganFlag = false;
            Toast.makeText(this, R.string.filterCleared, Toast.LENGTH_SHORT).show();
            assign();

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

    // Saves the state of the Buttons so they can be restored after the activity was destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("VeganFlag", veganFlag);
        outState.putBoolean("VegetarianFlag", vegetarianFlag);
        outState.putBoolean("GlutenFreeFlag", glutenFreeFlag);
        outState.putBoolean("DairyFreeFlag", dairyFreeFlag);
    }
}
