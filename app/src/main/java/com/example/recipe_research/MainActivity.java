package com.example.recipe_research;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    LoadingDialog loadingDialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    RandomRecipeResponseListener listener;
    SearchView searchView;

    Boolean _glutenFree;
    Boolean _vegetarian;
    Boolean _vegan;
    String tagString;
    String _query;

   Button _settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _glutenFree = false;
        _vegetarian = false;
        _vegan = false;
        _query = "";

        _settingsButton.findViewById(R.id.settings);



        loadingDialog = new LoadingDialog(MainActivity.this);

        searchView = findViewById(R.id.searchVieW_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                _query = query;
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
        PopupMenu popup = new PopupMenu(this, v, Gravity.RIGHT  );

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.filter_menu);



        if (_vegan){
            popup.getMenu().findItem(R.id.veganItem).setChecked(true);
        }if(_vegetarian){
            popup.getMenu().findItem(R.id.vegetarianItem).setChecked(true);
        }if(_glutenFree){
            popup.getMenu().findItem(R.id.glutenFreeItem).setChecked(true);
        }
        popup.show();
    }


    /**
     * Dismisses a dialog after a specified delay.
     *
     * @param loadingDialog The dialog to be dismissed.
     * @param delay         The delay in milliseconds before dismissing the dialog.
     */
    private void dismissDialogDelayed(long delay, LoadingDialog loadingDialog) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.disMiss();
            }
        }, delay);
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
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


            tagString = adapterView.getSelectedItem().toString();
            runRequest();

            /*
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            loadingDialog.showLoading();
            */

            /*dismissDialogDelayed(3000, loadingDialog);*/
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
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                    _vegan = false;
                    runRequest();
                }
                else {
                    menuItem.setChecked(true);
                    _vegan = true;
                    runRequest();
                }
                return true;
            case R.id.vegetarianItem:
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                    _vegetarian = false;
                    runRequest();

                }
                else {
                    menuItem.setChecked(true);
                    _vegetarian = true;
                    runRequest();
                }
                return true;
            case R.id.glutenFreeItem:
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                    _glutenFree = false;
                    runRequest();
                }
                else {
                    menuItem.setChecked(true);
                    _glutenFree = true;
                    runRequest();
                }
                return true;
            default:
                return false;
        }
    }

    public void runRequest(){

        tags.clear();

        String temp="";

        if(_vegetarian){

            temp = "vegetarian";

        }
        if(_vegan){

            if(temp.equals("")){
                temp = "vegan";
            }else {
                temp = temp +",vegan";
            }

        }
        if(_glutenFree){

            if(temp.equals("")){
                temp = "gluten free";
            }else {
                temp = temp +",gluten free";
            }

        }
        if(!tagString.equals("")){
            if(temp.equals("")){
                temp = tagString;
            }else {
                temp = temp +","+tagString;
            }
        }
        if(!_query.equals("")){
            if(temp.equals("")){
                temp = _query;
            }else {
                temp = temp +","+_query;
            }
        }
        tags.add(temp);

        manager.getRandomRecipes(randomRecipeResponseListener, tags);
        loadingDialog.showLoading();

    }


}

