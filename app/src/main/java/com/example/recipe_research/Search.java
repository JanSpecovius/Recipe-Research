package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

public class Search extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{
    String _recipeCategory;
    Button _searchButton;
    Button _menuButton;
    Button _settingsButton;
    Button _databaseButton;
    Switch _glutenFreeRadio;
    Switch _vegetarianRadio;
    Switch _veganRadio;
    Boolean _glutenFree;
    Boolean _vegetarian;
    Boolean _vegan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        _searchButton = findViewById(R.id.searchButton);
        _menuButton = findViewById(R.id.menuButton);
        _settingsButton = findViewById(R.id.settingsButton);
        _databaseButton = findViewById(R.id.databaseButton);

        _glutenFreeRadio = findViewById(R.id.glutenFreeRadio);
        _vegetarianRadio = findViewById(R.id.vegetarianRadio);
        _veganRadio = findViewById(R.id.veganRadio);


        _searchButton.setOnClickListener(this);

        _glutenFreeRadio.setOnClickListener(this);
        _vegetarianRadio.setOnClickListener(this);
        _veganRadio.setOnClickListener(this);
        _settingsButton.setOnClickListener(this);
        _databaseButton.setOnClickListener(this);



    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                _recipeCategory = "Main course";
                _menuButton.setText("Main course");
                return true;
            case R.id.item2:
                _recipeCategory = "Side dish";
                _menuButton.setText("Side dish");
                return true;
            case R.id.item3:
                _recipeCategory = "Dessert";
                _menuButton.setText("Dessert");
                return true;
            case R.id.item4:
                _recipeCategory = "Appetizer";
                _menuButton.setText("Appetizer");
                return true;
            case R.id.item5:
                _recipeCategory = "Salad";
                _menuButton.setText("Salad");
                return true;
            case R.id.item6:
                _recipeCategory = "Bread";
                _menuButton.setText("Bread");
                return true;
            case R.id.item7:
                _recipeCategory = "Breakfast";
                _menuButton.setText("Breakfast");
                return true;
            case R.id.item8:
                _recipeCategory = "Soup";
                _menuButton.setText("Soup");
                return true;
            case R.id.item9:
                _recipeCategory = "Beverage";
                _menuButton.setText("Beverage");
                return true;
            case R.id.item10:
                _recipeCategory = "Source";
                _menuButton.setText("Source");
                return true;
            case R.id.item11:
                _recipeCategory = "Marinade";
                _menuButton.setText("Marinade");
                return true;
            case R.id.item12:
                _recipeCategory = "Fingerfood";
                _menuButton.setText("Fingerfood");
                return true;
            case R.id.item13:
                _recipeCategory = "Snack";
                _menuButton.setText("Snack");
                return true;
            case R.id.item14:
                _recipeCategory = "Drink";
                _menuButton.setText("Drink");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v==_searchButton){
            getApiData(_recipeCategory, _glutenFree, _vegetarian, _vegan);
        }else if (v==_glutenFreeRadio){
            _glutenFree = _glutenFreeRadio.isChecked();
        }else if (v==_vegetarianRadio){
            _vegetarian = _vegetarianRadio.isChecked();
        }else if (v==_veganRadio){
            _vegan = _veganRadio.isChecked();
        } else if (v==_settingsButton) {
            Intent i = new Intent(Search.this, SettingsActivity.class);
            startActivity(i);
        } else if (v==_databaseButton) {
            Intent i = new Intent(Search.this, DatabaseActivity.class);
            startActivity(i);
        }
    }

    public void getApiData(String recipeCategory, Boolean glutenFree, Boolean vegetarian, Boolean vegan){

        //TODO: fetch Data
    }
}