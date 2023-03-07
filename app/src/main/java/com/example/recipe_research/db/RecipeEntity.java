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
    @NonNull
    public int apiID;

    @ColumnInfo(name = "vegetarian")
    @NonNull
    public boolean vegetarian;

    @ColumnInfo(name = "vegan")
    @NonNull
    public boolean vegan;

    @ColumnInfo(name = "gluten free")
    @NonNull
    public boolean glutenFree;

    @ColumnInfo(name = "dairy free")
    @NonNull
    public boolean dairyFree;

    @ColumnInfo(name = "readyInMinutes")
    public int readyInMinutes;

    @ColumnInfo(name = "servings")
    public int servings;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "sourceName")
    public String sourceName;

    @ColumnInfo(name = "spoonacularSourceUrl")
    public String spoonacularSourceUrl;

    @ColumnInfo(name = "summary")
    public String summary;

    @ColumnInfo(name = "date")
    public Date date;

}
