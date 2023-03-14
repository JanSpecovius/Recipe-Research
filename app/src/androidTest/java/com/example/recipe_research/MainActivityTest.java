package com.example.recipe_research;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.lang.Thread.sleep;

import androidx.annotation.ContentView;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class MainActivityTest {
    @Test
    public void test_standardElements() {
        ActivityScenario.launch(MainActivity.class);
        try {
            sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.cardView)).check(matches(isDisplayed()));
        onView(withId(R.id.recipeFilter)).check(matches(isDisplayed()));
        onView(withId(R.id.recipeFilter)).check(matches(withText("RECIPE-FILTER")));

        onView(withId(R.id.refresh)).check(matches(isDisplayed()));
        onView(withId(R.id.refresh)).check(matches(isClickable()));

        onView(withId(R.id.searchView_home)).check(matches(isDisplayed()));
    }

    @Test
    public void test_filterClickEntry(){
        ActivityScenario.launch(MainActivity.class);
        try {
            sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recipeFilter)).perform(click());
        onView(withText("Gluten Free")).check(matches(isDisplayed()));
        onView(withText("Gluten Free")).perform(click());

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recipeFilter)).perform(click());
        onView(withText("Dairy Free")).check(matches(isDisplayed()));
        onView(withText("Dairy Free")).perform(click());

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recipeFilter)).perform(click());
        onView(withText("Vegetarian")).check(matches(isDisplayed()));
        onView(withText("Vegetarian")).perform(click());

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recipeFilter)).perform(click());
        onView(withText("Vegan")).check(matches(isDisplayed()));
        onView(withText("Vegan")).perform(click());

    }
}
