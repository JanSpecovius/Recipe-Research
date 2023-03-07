package com.example.recipe_research.Models;

import java.util.ArrayList;
import java.util.List;

public class NutritionByIdResponse {
    public String calories;
    public String carbs;
    public String fat;
    public String protein;
    public ArrayList<Bad> bad;
    public ArrayList<Good> good;
    public long expires;
    public boolean isStale;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public ArrayList<Bad> getBad() {
        return bad;
    }

    public void setBad(ArrayList<Bad> bad) {
        this.bad = bad;
    }

    public ArrayList<Good> getGood() {
        return good;
    }

    public void setGood(ArrayList<Good> good) {
        this.good = good;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public boolean isStale() {
        return isStale;
    }

    public void setStale(boolean stale) {
        isStale = stale;
    }
}
