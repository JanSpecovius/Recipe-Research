package com.example.recipe_research.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "apiID")
    //@NonNull
    public int apiID;

    @ColumnInfo(name = "vegetarian")
    //@NonNull
    public boolean vegetarian;

    @ColumnInfo(name = "vegan")
    //@NonNull
    public boolean vegan;

    @ColumnInfo(name = "gluten free")
    //@NonNull
    public boolean glutenFree;

    @ColumnInfo(name = "dairy free")
    //@NonNull
    public boolean dairyFree;

    @ColumnInfo(name = "readyInMinutes")
    public int readyInMinutes;

    @ColumnInfo(name = "servings")
    public int servings;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "sourceName")
    public String sourceName;

    @ColumnInfo(name = "summary")
    public String summary;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "calories")
    public String calories;

    @ColumnInfo(name = "carbs")
    public String carbs;

    @ColumnInfo(name = "fat")
    public String fat;

    @ColumnInfo(name = "protein")
    public String protein;

    @ColumnInfo(name = "badName")
    public String badName;

    @ColumnInfo(name = "badAmount")
    public String badAmount;

    @ColumnInfo(name = "ingredients")
    public String ingredients;


}
