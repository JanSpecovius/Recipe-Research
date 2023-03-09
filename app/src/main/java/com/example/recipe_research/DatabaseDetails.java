package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.recipe_research.db.RecipeDao;
import com.example.recipe_research.db.RecipeDatabase;
import com.example.recipe_research.db.RecipeEntity;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class DatabaseDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_meal_name, textView_meal_source, textView_meal_summary, textView_meal_nutrition, textView_meal_ingredients, textView_creatTime;
    private ImageView _share,imageView_meal_name,_bookmark;
    private AlertDialog.Builder _builder;
    private String _title, _sourceName, _summary, _image, _url, _calories, _carbs, _fat, _protein, _badName, _badAmount,_ingredients;
    private int id;
    private RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        RecipeDatabase db = RecipeDatabase.getSingletonInstance(this);
        recipeDao = db.recipeDao();

        _builder = new AlertDialog.Builder(this);

        findViewById();

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        setDataFromDatabase(id);
        textView_creatTime.setText(recipeDao.getDateById(id).toString());

        assignRecipeDetail();
        assignNutritonDetail();

        _bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);
        _share.setOnClickListener(this);
        _bookmark.setOnClickListener(this);


    }


    private void findViewById() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        textView_meal_nutrition = findViewById(R.id.textView_meal_nutrition);
        imageView_meal_name = findViewById(R.id.imageView_meal_name);
        textView_meal_ingredients = findViewById(R.id.textView_meal_ingredients);
        textView_creatTime = findViewById(R.id.textView_meal_creatTime);



        _share = findViewById(R.id.share);
        _bookmark = findViewById(R.id.bookmark);
    }


    public void setDataFromDatabase(int id){
        RecipeEntity recipeEntity = recipeDao.getRecipeById(id);
        _title = recipeEntity.title;
        _sourceName = recipeEntity.sourceName;
        _summary = recipeEntity.summary;
        _image = recipeEntity.image;
        _url = recipeEntity.url;
        _calories = recipeEntity.calories;
        _carbs = recipeEntity.carbs;
        _fat = recipeEntity.fat;
        _protein = recipeEntity.protein;
        _badName = recipeEntity.badName;
        _badAmount = recipeEntity.badAmount;

        _ingredients = recipeEntity.ingredients;

    }


    @Override
    public void onClick(View v) {
        if (v == _share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool meal I found! " + _url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));

        } else if (v==_bookmark) {

            _builder.setTitle("Warning!!!");
            _builder.setMessage("Do you really want to delete this bookmark?");
            _builder.setCancelable(true);
            _builder.setPositiveButton(getString(R.string.yesButton), (dialogInterface, i) -> deleteFromDatabase());
            _builder.setNegativeButton(getString(R.string.noButton), (dialogInterface, i) -> dialogInterface.cancel());
            _builder.show();


        }
    }


    public void assignRecipeDetail() {
        textView_meal_name.setText(_title);
        textView_meal_source.setText(_sourceName);
        textView_meal_summary.setText(_summary);
        Picasso.get().load(_image).into(imageView_meal_name);


        textView_meal_ingredients.setText(_ingredients);

    }
    public void assignNutritonDetail(){


        StringBuilder sb = new StringBuilder();

        // Append the nutrition values to the string builder
        sb.append("Calories: ").append(_calories).append("\n");
        sb.append("Carbs: ").append(_carbs).append("\n");
        sb.append("Fat: ").append(_fat).append("\n");
        sb.append("Protein: ").append(_protein).append("\n");
        sb.append(_badName+": ").append(_badAmount).append("\n");

        // Set the string builder text to the text view
        textView_meal_nutrition.setText(sb.toString());

    }
    public void deleteFromDatabase(){
        recipeDao.deleteById(id);
        finish();
    }



}