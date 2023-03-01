package db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RecipeDao {

    @Query("SELECT COUNT(*) FROM RecipeEntity")
    public int getCount();

    @Insert
    public void insertRecipe(RecipeEntity recipeEntity);

    @Update
    public void updateRecipe(RecipeEntity recipeEntity);
}
