package com.example.recipe_research;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.ListFormatter;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

public class Search extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{
    String _recipeCategory;
    Button _menuButton;
    Button _settingsButton;
    Button _databaseButton;
    Boolean _glutenFree;
    Boolean _vegetarian;
    Boolean _vegan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        _menuButton = findViewById(R.id.menuButton);
        _settingsButton = findViewById(R.id.settingsButton);
        _databaseButton = findViewById(R.id.databaseButton);
        _vegan = false;
        _vegetarian = false;
        _glutenFree = false;

        _settingsButton.setOnClickListener(this);
        _databaseButton.setOnClickListener(this);
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v, Gravity.LEFT);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    public void showFilter(View v) {
        PopupMenu popup = new PopupMenu(this, v, Gravity.RIGHT  );

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.fliter_menu);



        if (_vegan){
            popup.getMenu().findItem(R.id.veganItem).setChecked(true);
        }if(_vegetarian){
            popup.getMenu().findItem(R.id.vegetarianItem).setChecked(true);
        }if(_glutenFree){
            popup.getMenu().findItem(R.id.glutenFreeItem).setChecked(true);
        }
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
            case R.id.veganItem:
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                    _vegan = false;
                }
                else {
                    menuItem.setChecked(true);
                    _vegan = true;
                }
                return true;
            case R.id.vegetarianItem:
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                    _vegetarian = false;
                }
                else {
                    menuItem.setChecked(true);
                    _vegetarian = true;
                }
                return true;
            case R.id.glutenFreeItem:
                if(menuItem.isChecked()){
                    menuItem.setChecked(false);
                    _glutenFree = false;
                }
                else {
                    menuItem.setChecked(true);
                    _glutenFree = true;


                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
       if (v==_settingsButton) {
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