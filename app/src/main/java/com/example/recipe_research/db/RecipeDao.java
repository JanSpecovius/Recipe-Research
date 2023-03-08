package com.example.recipe_research.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecipeDao {

    @Query("SELECT COUNT(*) FROM RecipeEntity")
    int getCount();

    @Query("SELECT * FROM RecipeEntity WHERE id =:id")
    RecipeEntity getRecipe(int id);

    @Query("SELECT * FROM RecipeEntity")
    RecipeEntity[] getAllRecipes();

    @Insert
    void insert(RecipeEntity recipeEntity);

    @Delete
    void delete(RecipeEntity recipeEntity);

}
