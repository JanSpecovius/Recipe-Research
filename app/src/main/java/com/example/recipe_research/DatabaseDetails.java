package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.recipe_research.db.RecipeDao;
import com.example.recipe_research.db.RecipeDatabase;
import com.example.recipe_research.db.RecipeEntity;
import com.squareup.picasso.Picasso;

public class DatabaseDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_meal_name, textView_meal_source, textView_meal_summary, textView_meal_nutrition, textView_meal_ingredients, textView_createTime;
    private ImageView _share, imageView_meal_name, _bookmark;
    private AlertDialog.Builder _builder;
    private String _title, _sourceName, _summary, _image, _url, _calories, _carbs, _fat, _protein, _badName, _badAmount, _ingredients;
    private int id;
    private RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_details);


        assign();



        setDataFromDatabase(id);


        assignRecipeDetail();
        assignNutritonDetail();

    }

    private void assign() {
        textView_meal_name = findViewById(R.id.textView_db_meal_name);
        textView_meal_source = findViewById(R.id.textView_db_meal_source);
        textView_meal_summary = findViewById(R.id.textView_db_meal_summary);
        textView_meal_nutrition = findViewById(R.id.textView_db_meal_nutrition);
        imageView_meal_name = findViewById(R.id.imageView_db_meal_name);
        textView_meal_ingredients = findViewById(R.id.textView_db_meal_ingredients);
        textView_createTime = findViewById(R.id.textView_db_meal_createTime);
        _share = findViewById(R.id.imageView_db_share);
        _bookmark = findViewById(R.id.imageView_db_bookmark);



        recipeDao = RecipeDatabase.getSingletonInstance(this).recipeDao();

        _builder = new AlertDialog.Builder(this);
        id = Integer.parseInt(getIntent().getStringExtra(getString(R.string.id)));

        textView_createTime.setText(getString(R.string.db_timestamp) + recipeDao.getDateById(id).toString());
        _bookmark.setBackgroundResource(R.drawable.ic_baseline_bookmark);

        _share.setOnClickListener(this);
        _bookmark.setOnClickListener(this);



    }

    public void setDataFromDatabase(int id) {
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.meal_found_mail) + _url);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));

        } else if (v == _bookmark) {

            _builder.setTitle(R.string.builder_title);
            _builder.setMessage(getString(R.string.bookmark_msg));
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
        textView_meal_ingredients.setText(_ingredients);
        Picasso.get().load(_image).into(imageView_meal_name);
    }

    public void assignNutritonDetail() {
        // Append the nutrition values to the string builder
        String sb = R.string.calories + _calories + "\n" +
                R.string.carbs + _carbs + "\n" +
                R.string.fat + _fat + "\n" +
                R.string.protein + _protein + "\n" +
                _badName + getString(R.string.dp) + _badAmount + "\n";

        // Set the string builder text to the text view
        textView_meal_nutrition.setText(sb);
    }

    public void deleteFromDatabase() {
        recipeDao.deleteById(id);
        finish();
    }
}