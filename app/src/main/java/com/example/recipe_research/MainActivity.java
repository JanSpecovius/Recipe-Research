package com.example.recipe_research;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_research.Adapters.RandomRecipeAdapter;
import com.example.recipe_research.Listeners.RandomRecipeResponseListener;
import com.example.recipe_research.Listeners.RecipeClickListener;
import com.example.recipe_research.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {
    private LoadingDialog loadingDialog;
    private RequestManager manager;
    private final List<String> tags = new ArrayList<>();
    private Boolean glutenFree;
    private Boolean vegetarian;
    private Boolean dairyFree;
    private Boolean vegan;
    private String tagString;
    private String query;
    private Button databaseButton;
    private ImageView refresh;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        } else {
            setContentView(R.layout.activity_main);
        }
        assign();
    }

    private void assign() {
        Spinner spinner;
        databaseButton = findViewById(R.id.database);
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        databaseButton.setOnClickListener(this);

        loadingDialog = new LoadingDialog(MainActivity.this);
        manager = new RequestManager(this);

        glutenFree = false;
        vegetarian = false;
        vegan = false;
        dairyFree = false;
        query = "";

        searchView = findViewById(R.id.searchVieW_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryResult) {
                query = queryResult;
                runRequest();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);
    }

    public void showFilter(View v) {
        PopupMenu popup = new PopupMenu(this, v, Gravity.END);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.filter_menu);

        if (vegan) {
            popup.getMenu().findItem(R.id.veganItem).setChecked(true);
        }
        if (vegetarian) {
            popup.getMenu().findItem(R.id.vegetarianItem).setChecked(true);
        }
        if (glutenFree) {
            popup.getMenu().findItem(R.id.glutenFreeItem).setChecked(true);
        }
        if (dairyFree) {
            popup.getMenu().findItem(R.id.lactoseFreeItem).setChecked(true);
        }
        popup.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            loadingDialog.disMiss();
            RecyclerView recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            RandomRecipeAdapter randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            loadingDialog.disMiss();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            tagString = adapterView.getSelectedItem().toString();

            clearSearchView();
            runRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(MainActivity.this, getString(R.string.nothing_selected), Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = id -> startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
            .putExtra(getString(R.string.id), id));

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.veganItem:
                if (vegan) {
                    vegan = false;
                    clearSearchView();
                    runRequest();
                } else {
                    vegan = true;
                    vegetarian = false;
                    clearSearchView();
                    runRequest();
                }
                return true;
            case R.id.vegetarianItem:
                if (vegetarian) {
                    vegetarian = false;
                    clearSearchView();
                    runRequest();

                } else {
                    vegetarian = true;
                    vegan = false;
                    clearSearchView();
                    runRequest();
                }
                return true;
            case R.id.glutenFreeItem:
                if (menuItem.isChecked()) {
                    glutenFree = false;
                    clearSearchView();
                    runRequest();
                } else {
                    glutenFree = true;
                    clearSearchView();
                    runRequest();
                }
                return true;
            case R.id.lactoseFreeItem:
                if (menuItem.isChecked()) {
                    dairyFree = false;
                    clearSearchView();
                    runRequest();
                } else {
                    dairyFree = true;
                    clearSearchView();
                    runRequest();
                }
                return true;
            default:
                return false;
        }
    }

    // Encapsulated method that handles requests for recipes by building search query based on user input and executing on a separate thread.
    public void runRequest() {
        tags.clear();
        loadingDialog.showLoading();

        String temp = "";

        if (vegetarian) {
            temp = "vegetarian";
        }
        if (vegan) {
            if (temp.equals("")) {
                temp = "vegan";
            } else {
                temp = temp + ",vegan";
            }
        }
        if (glutenFree) {
            if (temp.equals("")) {
                temp = "gluten free";
            } else {
                temp = temp + ",gluten free";
            }
        }
        if (dairyFree) {
            if (temp.equals("")) {
                temp = "dairy free";
            } else {
                temp = temp + ",dairy free";
            }
        }
        if (!tagString.equals("")) {
            if (temp.equals("")) {
                temp = tagString;
            } else {
                temp = temp + "," + tagString;
            }
        }
        if (!query.equals("")) {
            if (temp.equals("")) {
                temp = query;
            } else {
                temp = temp + "," + query;
            }
        }
        tags.add(temp);

        Thread thread = new Thread(() -> manager.getRandomRecipes(randomRecipeResponseListener, tags));
        thread.start();
    }

    @Override
    public void onClick(View v) {
        if (v == databaseButton) {
            Intent i = new Intent(MainActivity.this, DatabaseActivity.class);
            startActivity(i);
        } else if (v == refresh) {
            runRequest();
        }
    }
    private void clearSearchView() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        query = "";
    }
}

