package com.example.recipe_research.Listeners;

import com.example.recipe_research.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);

    void didError(String message);
}