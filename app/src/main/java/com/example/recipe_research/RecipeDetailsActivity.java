package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.recipe_research.Models.Bad;
import com.example.recipe_research.Models.NutritionByIdResponse;
import com.example.recipe_research.Models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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


    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViewById();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
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
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_name);
            url = response.spoonacularSourceUrl;






            StringBuilder sb = new StringBuilder();

            int i = response.getExtendedIngredients().size();
            int j = 0;
            while (i>j){
                String ingrName = response.getExtendedIngredients().get(j).name;
                String ingrAmt = String.valueOf(response.getExtendedIngredients().get(j).measures.metric.amount);
                String ingrUnit = String.valueOf(response.getExtendedIngredients().get(j).measures.metric.unitLong);

                sb.append(ingrName+": ").append(ingrAmt+" ").append(ingrUnit).append("\n");
                j++;
            }

            j=0;
            sb.append("\n");
            while (i>j){

                String ingrAmt = String.valueOf(response.getExtendedIngredients().get(j).original);
                sb.append(ingrAmt).append("\n");
                j++;
            }










            // Set the string builder text to the text view
            textView_meal_ingredients.setText(sb.toString());

        }


        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final NutritionByIdListener nutritionByIdListener = new NutritionByIdListener() {
        @Override
        public void onNutritionByIdReceived(NutritionByIdResponse nutrition, String message) {

            StringBuilder sb = new StringBuilder();

            // Get the nutrition values from the response object
            String calories = nutrition.getCalories();
            String carbs = nutrition.getCarbs();
            String fat = nutrition.getFat();
            String protein = nutrition.getProtein();
            String badName = nutrition.getBad().get(4).title;
            String badAmount = nutrition.getBad().get(4).amount;


            // Append the nutrition values to the string builder
            sb.append("Calories: ").append(calories).append("\n");
            sb.append("Carbs: ").append(carbs).append("\n");
            sb.append("Fat: ").append(fat).append("\n");
            sb.append("Protein: ").append(protein).append("\n");
            sb.append(badName+": ").append(badAmount).append("\n");


            // Set the string builder text to the text view
            textView_meal_nutrition.setText(sb.toString());
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool meal I found! " + url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));

        } else if (v == _bookmark) {
            Toast.makeText(RecipeDetailsActivity.this, "Daniel mach die Datenbank", Toast.LENGTH_SHORT).show();
            //write code here to add a new Database entry @Daniel
        }
    }
}