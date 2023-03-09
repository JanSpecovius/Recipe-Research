package com.example.recipe_research;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private RandomRecipeAdapter randomRecipeAdapter;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private List<String> tags = new ArrayList<>();
    private SearchView searchView;
    private Boolean _glutenFree, _vegetarian,_lactoseFree,_vegan,flag;
    private String tagString,_query;
    private Button _databaseButton;
    private ImageView _refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        } else {
            setContentView(R.layout.activity_main);
        }
        flag = false;

        loadingDialog = new LoadingDialog(MainActivity.this);

        _glutenFree = false;
        _vegetarian = false;
        _vegan = false;
        _lactoseFree = false;
        _query = "";

        _databaseButton = findViewById(R.id.database);
        _refresh = findViewById(R.id.refresh);
        _refresh.setOnClickListener(this);
        _databaseButton.setOnClickListener(this);


        searchView = findViewById(R.id.searchVieW_home);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                runRequest();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);
    }

    public void showFilter(View v) {
        PopupMenu popup = new PopupMenu(this, v, Gravity.RIGHT);

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.filter_menu);

        if (_vegan) {
            popup.getMenu().findItem(R.id.veganItem).setChecked(true);
        }
        if (_vegetarian) {
            popup.getMenu().findItem(R.id.vegetarianItem).setChecked(true);
        }
        if (_glutenFree) {
            popup.getMenu().findItem(R.id.glutenFreeItem).setChecked(true);
        }
        if (_lactoseFree) {
            popup.getMenu().findItem(R.id.lactoseFreeItem).setChecked(true);
        }
        popup.show();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {

        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            loadingDialog.disMiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
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

            runRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.veganItem:
                if (_vegan) {
                    _vegan = false;
                    runRequest();
                } else {
                    _vegan = true;
                    _vegetarian = false;
                    runRequest();
                }
                return true;
            case R.id.vegetarianItem:
                if (_vegetarian) {
                    _vegetarian = false;
                    runRequest();

                } else {
                    _vegetarian = true;
                    _vegan = false;
                    runRequest();
                }
                return true;
            case R.id.glutenFreeItem:
                if (menuItem.isChecked()) {
                    _glutenFree = false;
                    runRequest();
                } else {
                    _glutenFree = true;
                    runRequest();
                }
                return true;
            case R.id.lactoseFreeItem:
                if (menuItem.isChecked()) {
                    _lactoseFree = false;
                    runRequest();
                } else {
                    _lactoseFree = true;
                    runRequest();
                }
                return true;
            default:
                return false;
        }
    }

    public void runRequest() {
        tags.clear();
        loadingDialog.showLoading();

        String temp = "";

        if (_vegetarian) {
            temp = "vegetarian";
        }
        if (_vegan) {
            if (temp.equals("")) {
                temp = "vegan";
            } else {
                temp = temp + ",vegan";
            }
        }
        if (_glutenFree) {

            if (temp.equals("")) {
                temp = "gluten free";
            } else {
                temp = temp + ",gluten free";
            }
        }
        if (_lactoseFree) {

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
        if (!_query.equals("")) {
            if (temp.equals("")) {
                temp = _query;
            } else {
                temp = temp + "," + _query;
            }
        }
        tags.add(temp);


        new Thread(() -> {
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
        }).start();


    }


    @Override
    public void onClick(View v) {
        if (v == _databaseButton) {
            Intent i = new Intent(MainActivity.this, DatabaseActivity.class);
            startActivity(i);
        }else if(v == _refresh){
            runRequest();
        }
    }
}

