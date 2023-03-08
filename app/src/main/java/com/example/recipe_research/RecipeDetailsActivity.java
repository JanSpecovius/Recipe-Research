package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe_research.Adapters.IngredientsAdapter;
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
    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary, textView_meal_nutrition, textView_meal_ingredients;
    ImageView imageView_meal_name;
    RecyclerView recycler_meal_ingredients;
    RequestManager manager;
    LoadingDialog loadingDialog;
    IngredientsAdapter ingredientsAdapter;
    ImageView _share;
    ImageView _bookmark;


    int _id;
    String _title, _sourceName, _summary, _image, _url, _calories, _carbs, _fat, _protein, _badName, _badAmount;
    int _amount, _readyInTime, _servings;

    boolean _glutenfree, _vegetarian, _vegan, _dairyFree;
    String[] _ingrArray;

    private RecipeDatabase db;

    private RecipeDao recipeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        db = RecipeDatabase.getSingletonInstance(this);
        recipeDao = db.recipeDao();


        findViewById();

        id = Integer.parseInt(getIntent().getStringExtra(getString(R.string.id)));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getNutritionById(nutritionByIdListener, id);
        loadingDialog = new LoadingDialog(this);
        _share.setOnClickListener(this);
        _bookmark.setOnClickListener(this);
        loadingDialog.showLoading();


    }


    private void findViewById() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        textView_meal_nutrition = findViewById(R.id.textView_meal_nutrition);
        imageView_meal_name = findViewById(R.id.imageView_meal_name);
        textView_meal_ingredients = findViewById(R.id.textView_meal_ingredients);


        _share = findViewById(R.id.share);
        _bookmark = findViewById(R.id.bookmark);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            loadingDialog.disMiss();

            _title = response.title;
            _sourceName = response.sourceName;
            _summary = response.summary;
            _image = response.image;
            _url = response.spoonacularSourceUrl;
            _id = response.id;
            _readyInTime = response.readyInMinutes;
            _servings = response.servings;


            _glutenfree = response.glutenFree;
            _vegetarian = response.vegetarian;
            _vegan = response.vegan;
            _dairyFree = response.dairyFree;


           

            /*
            int i = response.getExtendedIngredients().size();
            int j = 0;
            while (i>j){
                String ingrName = response.getExtendedIngredients().get(j).name;
                String ingrAmt = String.valueOf(response.getExtendedIngredients().get(j).measures.metric.amount);
                String ingrUnit = String.valueOf(response.getExtendedIngredients().get(j).measures.metric.unitLong);

                sb.append(ingrName+": ").append(ingrAmt+" ").append(ingrUnit).append("\n");
                j++;
            }

             */
            _amount = response.getExtendedIngredients().size();
            int counter = 0;

            _ingrArray = new String[_amount];

            while (_amount > counter) {

                _ingrArray[counter] = String.valueOf(response.getExtendedIngredients().get(counter).original);
                counter++;
            }


            assignRecipeDetail();
        }


        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final NutritionByIdListener nutritionByIdListener = new NutritionByIdListener() {
        @Override
        public void onNutritionByIdReceived(NutritionByIdResponse nutrition, String message) {


            //Get the nutrition values from the response object
            _calories = nutrition.getCalories();
            _carbs = nutrition.getCarbs();
            _fat = nutrition.getFat();
            _protein = nutrition.getProtein();
            _badName = nutrition.getBad().get(4).title;
            _badAmount = nutrition.getBad().get(4).amount;


            assignNutritonDetail();

        }

        @Override
        public void onNutritionByIdError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        if (v == _share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.meal_found_mail) + _url);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));

        } else if (v == _bookmark) {

            //TODO write code here to add a new Database entry @Daniel
            insertRow();
        }
    }


    public void assignRecipeDetail() {
        textView_meal_name.setText(_title);
        textView_meal_source.setText(_sourceName);
        textView_meal_summary.setText(_summary);
        Picasso.get().load(_image).into(imageView_meal_name);

        StringBuilder sb = new StringBuilder();

        int counter = 0;

        while (_amount > counter) {

            sb.append(_ingrArray[counter]).append("\n");
            counter++;
        }

        // Set the string builder text to the text view
        textView_meal_ingredients.setText(sb.toString());

    }

    public void assignNutritonDetail() {


        StringBuilder sb = new StringBuilder();

        // Append the nutrition values to the string builder
        sb.append(getString(R.string.calories)).append(_calories).append("\n");
        sb.append(getString(R.string.carbs)).append(_carbs).append("\n");
        sb.append(getString(R.string.fat)).append(_fat).append("\n");
        sb.append(getString(R.string.protein)).append(_protein).append("\n");
        sb.append(_badName + ": ").append(_badAmount).append("\n");

        // Set the string builder text to the text view
        textView_meal_nutrition.setText(sb.toString());


        sb = new StringBuilder();

        int counter = 0;

        while (_amount > counter) {

            sb.append(_ingrArray[counter]).append("\n");
            counter++;
        }

        //Set the string builder text to the text view
        textView_meal_ingredients.setText(sb.toString());


    }


    public void insertRow() {
        RecipeEntity entity = new RecipeEntity();
        entity.apiID = _id;
        entity.title = _title;
        entity.dairyFree = _dairyFree;
        entity.glutenFree = _glutenfree;
        entity.vegan = _vegan;
        entity.vegetarian = _vegetarian;
        entity.image = _image;
        entity.sourceName = _sourceName;
        entity.summary = _summary;
        entity.url = _url;
        entity.calories = _calories;
        entity.carbs = _carbs;
        entity.fat = _fat;
        entity.protein = _protein;
        entity.badName = _badName;
        entity.badAmount = _badAmount;
        entity.readyInMinutes = _readyInTime;
        entity.servings = _servings;


        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < _amount; i++) {
            temp.append(_ingrArray[i]).append(" | ");
        }
        entity.ingredients = temp.toString();

        entity.date = new Date();

        recipeDao.insert(entity);
    }
}