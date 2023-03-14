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

    @Query("SELECT date FROM RecipeEntity WHERE id =:id")
    Date getDateById(int id);

    @Query("DELETE FROM RecipeEntity WHERE id =:id")
    void deleteById(int id);

    @Query("DELETE FROM RecipeEntity WHERE apiID =:apiID")
    void deleteByApiId(int apiID);

    @Query("DELETE FROM RecipeEntity")
    void deleteAll();

}
