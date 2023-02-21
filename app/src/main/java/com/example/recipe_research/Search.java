package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    Button _glutenFreeSwitch;
    Button _vegetarianSwitch;
    Button _veganSwitch;
    Boolean _glutenFree;
    Boolean _vegetarian;
    Boolean _vegan;

    Boolean flag1;
    Boolean flag2;
    Boolean flag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        _searchButton = findViewById(R.id.searchButton);
        _menuButton = findViewById(R.id.menuButton);
        _settingsButton = findViewById(R.id.settingsButton);
        _databaseButton = findViewById(R.id.databaseButton);
        flag1= true;
        flag2= true;
        flag3= true;

        _glutenFreeSwitch = findViewById(R.id.glutenFreeSwitch);
        _vegetarianSwitch = findViewById(R.id.vegetarianSwitch);
        _veganSwitch = findViewById(R.id.veganSwitch);


        _searchButton.setOnClickListener(this);

        _glutenFreeSwitch.setOnClickListener(this);
        _vegetarianSwitch.setOnClickListener(this);
        _veganSwitch.setOnClickListener(this);
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
        }else if (v== _glutenFreeSwitch){
            _glutenFree = _glutenFreeSwitch.isPressed();
            if(flag1 == true){
                _glutenFreeSwitch.setBackgroundColor(Color.BLACK);
                _glutenFree = true;
                flag1=false;
            }else{
                _glutenFreeSwitch.setBackgroundColor(Color.BLUE);
                _glutenFree = false;
                flag1 = true;
            }

        }else if (v== _vegetarianSwitch){
            _vegetarian = _vegetarianSwitch.isPressed();
            if(flag2 == true){
                _vegetarianSwitch.setBackgroundColor(Color.BLACK);
                _vegetarian = true;
                flag2=false;
            }else{
                _vegetarianSwitch.setBackgroundColor(Color.BLUE);
                _vegetarian = false;
                flag2 = true;
            }
        }else if (v== _veganSwitch){
            _vegan = _veganSwitch.isPressed();
            if(flag3 == true){
                _veganSwitch.setBackgroundColor(Color.BLACK);
                _vegan = true;
                flag3=false;
            }else{
                _veganSwitch.setBackgroundColor(Color.BLUE);
                _vegan = false;
                flag3 = true;
            }
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