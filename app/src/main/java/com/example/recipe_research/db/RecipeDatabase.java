package com.example.recipe_research.db;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.util.Log;


@androidx.room.Database(entities = {RecipeEntity.class}, version = 1, exportSchema = false)
@TypeConverters({RecipeTypeConverter.class})

public abstract class RecipeDatabase extends RoomDatabase {
    private static final String DB_FILE_NAME = "recipe.db";
    private static RecipeDatabase SINGLETON_INSTANCE = null;
    public abstract RecipeDao recipeDao();
    public static RecipeDatabase getSingletonInstance(android.content.Context context) {
        if (SINGLETON_INSTANCE == null) {

            SINGLETON_INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, DB_FILE_NAME)
                            .allowMainThreadQueries()
                            .build();
            Log.i("DatabaseLogging", "Database created");
        }
        return SINGLETON_INSTANCE;
    }


}
