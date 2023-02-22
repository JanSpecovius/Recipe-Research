package com.example.recipe_research;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class UiTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRuleMain
            = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityScenarioRule<Search> activityScenarioRuleSearch
            = new ActivityScenarioRule<>(Search.class);

    @Test
    public void graphicDoesExist() {
        onView(withId(R.id.lottieAnimationView)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.lottieAnimationView)).check(matches(isNotClickable()));

    }

    @Test
    public void switchActivity(){
        onView(withId(R.id.startButton)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.startButton)).check(matches(isClickable()));
        onView(withId(R.id.startButton)).check(matches(withText("Start")));
        onView(withId(R.id.startButton)).perform(click());

    }


    @Test
    public void searchActivity() {


        onView(withId(R.id.veganSwitch)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.glutenFreeSwitch)).perform(click()).check(matches(isClickable())).check(matches(withText("Glutenfrei"))).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.vegetarianSwitch)).perform(click()).check(matches(isClickable())).check(matches(withText("Vegetarisch"))).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.veganSwitch)).perform(click()).check(matches(isClickable())).check(matches(withText("Vegan"))).check(matches(isCompletelyDisplayed()));

        String[] testArgs = new String[]{"Main course","Side dish", "Dessert", "Appetizer", "Salad", "Bread", "Breakfast", "Soup", "Beverage", "Source", "Marinade", "Fingerfood", "Snack", "Drink"};

        for (String testArg : testArgs) {
            onView(withId(R.id.menuButton)).check(matches(isCompletelyDisplayed())).check(matches(isClickable())).perform(click());
            onView(withText(testArg)).check(matches(isCompletelyDisplayed())).perform(click());
            onView(withId(R.id.menuButton)).check(matches(withText(testArg)));
        }
    }
}