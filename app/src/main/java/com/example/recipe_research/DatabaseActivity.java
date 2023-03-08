package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipe_research.Adapters.RandomRecipeAdapter;
import com.example.recipe_research.Models.MealsFromDatabase;

import com.example.recipe_research.Listeners.RecipeClickListener;
import com.example.recipe_research.Models.MealsFromDatabase;
import com.example.recipe_research.Models.Recipe;
import com.example.recipe_research.db.RecipeDao;
import com.example.recipe_research.db.RecipeDatabase;
import com.example.recipe_research.db.RecipeEntity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;



public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    RandomRecipeAdapter recipeAdapter;
    Button _delete;
    private AlertDialog.Builder _builder;

    private RecipeDatabase db;
    private RecipeDao recipeDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        _builder = new AlertDialog.Builder(this);
        _delete = findViewById(R.id.deleteAll);
        _delete.setOnClickListener(this);

        assign();


    }



    public void assign(){
        recyclerView = findViewById(R.id.recycler_database);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(DatabaseActivity.this, 1));
        recipeAdapter = new RandomRecipeAdapter(DatabaseActivity.this, fetchData(), recipeClickListener);
        recyclerView.setAdapter(recipeAdapter);
    }
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {

                startActivity(new Intent(DatabaseActivity.this, DatabaseDetails.class)
                        .putExtra("id", id));

        }
    };

    private List<Recipe> fetchData(){

        List<Recipe> recipe = new ArrayList<>();
        // Create an object of MealsFromDatabase class
        Recipe meal;
        db = RecipeDatabase.getSingletonInstance(this);
        recipeDao = db.recipeDao();

        for(int i = 0; i < recipeDao.getCount(); i++){
            RecipeEntity[] recipeEntity = recipeDao.getAllRecipes();
            meal = toRecipe(recipeEntity[i]);
            recipe.add(meal);
        }

        return recipe;
    }

    public Recipe toRecipe(RecipeEntity re){
        Recipe recipe = new Recipe();
        recipe.title = re.title;
        recipe.image = re.image;
        recipe.servings = re.servings;
        recipe.readyInMinutes = re.readyInMinutes;
        recipe.vegan = re.vegan;
        recipe.vegetarian = re.vegetarian;
        recipe.glutenFree = re.glutenFree;
        recipe.dairyFree = re.dairyFree;

        return recipe;
    }


    @Override
    public void onClick(View v) {
        if(v==_delete){
            _builder.setTitle("Warning!!!");
            _builder.setMessage("Do you really want to delete all bookmarks?");
            _builder.setCancelable(true);
            _builder.setPositiveButton(getString(R.string.yesButton), (dialogInterface, i) -> delete());
            _builder.setNegativeButton(getString(R.string.noButton), (dialogInterface, i) -> dialogInterface.cancel());
            _builder.show();
        }
    }
    public void delete(){
        db = RecipeDatabase.getSingletonInstance(this);
        recipeDao = db.recipeDao();
        recipeDao.deleteAll();
        Toast.makeText(this, "All bookmarks deleted", Toast.LENGTH_SHORT).show();
        assign();
    }
}
