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
    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void AgraphicDoesExist() {
        onView(withId(R.id.lottieAnimationView)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.lottieAnimationView)).check(matches(isNotClickable()));
    }

    @Test
    public void BtoSearchActivity(){
        onView(withId(R.id.startButton)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.startButton)).check(matches(isClickable()));
        onView(withId(R.id.startButton)).check(matches(withText("Start")));
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.veganRadio)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void CradioButtons(){
        //onView(withId(R.id.veganRadio)).perform(click()).check(matches(isClickable())).check(matches(withText("Vegan"))).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.glutenFreeRadio)).check(matches(isCompletelyDisplayed()));
    }

}