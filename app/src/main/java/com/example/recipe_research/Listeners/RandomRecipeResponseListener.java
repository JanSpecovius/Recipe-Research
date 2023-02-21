package com.example.recipe_research.Listeners;

import com.example.recipe_research.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);

    void didError(String message);
}
