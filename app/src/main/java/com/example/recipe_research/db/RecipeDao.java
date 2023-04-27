package com.example.recipe_research.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;

@Dao
public interface RecipeDao {

    @Insert
    void insert(RecipeEntity recipeEntity);

    @Query("SELECT COUNT(*) FROM RecipeEntity")
    int getCount();

    @Query("SELECT * FROM RecipeEntity WHERE id =:id")
    RecipeEntity getRecipeById(int id);

    @Query("SELECT * FROM RecipeEntity WHERE apiID =:apiID")
    RecipeEntity getRecipeByApiId(int apiID);

    @Query("SELECT * FROM RecipeEntity")
    RecipeEntity[] getAllRecipes();

    @Query("SELECT * FROM RecipeEntity WHERE `gluten free` = 1")
    RecipeEntity[] getGlutenfreeRecipes();

    @Query("SELECT COUNT(*) FROM RecipeEntity WHERE `gluten free` = 1")
    int getGlutenfreeCount();

    @Query("SELECT * FROM RecipeEntity WHERE `dairy free` = 1")
    RecipeEntity[] getDairyfreeRecipes();

    @Query("SELECT COUNT(*) FROM RecipeEntity WHERE `dairy free` = 1")
    int getDairyfreeCount();

    @Query("SELECT * FROM RecipeEntity WHERE vegetarian = 1")
    RecipeEntity[] getVegetarianRecipes();

    @Query("SELECT COUNT(*) FROM RecipeEntity WHERE vegetarian = 1")
    int getVegetarianCount();

    @Query("SELECT * FROM RecipeEntity WHERE vegan = 1")
    RecipeEntity[] getVeganRecipes();

    @Query("SELECT COUNT(*) FROM RecipeEntity WHERE vegan = 1")
    int getVeganCount();

    @Query("SELECT date FROM RecipeEntity WHERE id =:id")
    Date getDateById(int id);

    @Query("DELETE FROM RecipeEntity WHERE id =:id")
    void deleteById(int id);

    @Query("DELETE FROM RecipeEntity WHERE apiID =:apiID")
    void deleteByApiId(int apiID);

    @Query("DELETE FROM RecipeEntity")
    void deleteAll();

}
