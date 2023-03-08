package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipe_research.Adapters.RandomRecipeAdapter;
import com.example.recipe_research.Models.MealsFromDatabase;

import com.example.recipe_research.Listeners.RecipeClickListener;
import com.example.recipe_research.Models.MealsFromDatabase;
import com.example.recipe_research.Models.Recipe;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;



public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    RandomRecipeAdapter recipeAdapter;
    Button _delete;
    private AlertDialog.Builder _builder;

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
        Recipe meal = new Recipe();
        meal.title = "Spaghetti Carbonara";
        meal.image = "https://example.com/spaghetti.jpg";
        meal.servings = 4;
        meal.readyInMinutes = 30;
        meal.vegan = false;
        meal.vegetarian = false;
        meal.glutenFree = false;
        meal.dairyFree = false;

        recipe.add(meal);

        meal = new Recipe();

        meal.title = "4353453";
        meal.image = "https://example.com/spaghetti.jpg";
        meal.servings = 4;
        meal.readyInMinutes = 300;
        meal.vegan = false;
        meal.vegetarian = false;
        meal.glutenFree = false;
        meal.dairyFree = false;

        recipe.add(meal);

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
    public void delete(){}
}
