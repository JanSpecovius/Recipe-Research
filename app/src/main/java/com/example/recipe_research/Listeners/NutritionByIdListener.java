package com.example.recipe_research.Listeners;

import com.example.recipe_research.Models.NutritionByIdResponse;

public interface NutritionByIdListener {
    void onNutritionByIdReceived(NutritionByIdResponse nutrition, String message);

    void onNutritionByIdError(String message);
}
