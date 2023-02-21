package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;

public class Search extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{
    String _recipeCategory;
    Button _searchButton;
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

        _glutenFreeRadio = findViewById(R.id.glutenFreeRadio);
        _vegetarianRadio = findViewById(R.id.vegetarianRadio);
        _veganRadio = findViewById(R.id.veganRadio);

        _searchButton.setOnClickListener(this);

        _glutenFreeRadio.setOnClickListener(this);
        _vegetarianRadio.setOnClickListener(this);
        _veganRadio.setOnClickListener(this);



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
                return true;
            case R.id.item2:
                _recipeCategory = "Side dish";
                return true;
            case R.id.item3:
                _recipeCategory = "Dessert";
                return true;
            case R.id.item4:
                _recipeCategory = "Appetizer";
                return true;
            case R.id.item5:
                _recipeCategory = "Salad";
                return true;
            case R.id.item6:
                _recipeCategory = "Bread";
                return true;
            case R.id.item7:
                _recipeCategory = "Breakfast";
                return true;
            case R.id.item8:
                _recipeCategory = "Soup";
                return true;
            case R.id.item9:
                _recipeCategory = "Beverage";
                return true;
            case R.id.item10:
                _recipeCategory = "Source";
                return true;
            case R.id.item11:
                _recipeCategory = "Marinade";
                return true;
            case R.id.item12:
                _recipeCategory = "Fingerfood";
                return true;
            case R.id.item13:
                _recipeCategory = "Snack";
                return true;
            case R.id.item14:
                _recipeCategory = "Drink";
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
        }
    }

    public void getApiData(String recipeCategory, Boolean glutenFree, Boolean vegetarian, Boolean vegan){

        //TODO: fetch Data
    }
}