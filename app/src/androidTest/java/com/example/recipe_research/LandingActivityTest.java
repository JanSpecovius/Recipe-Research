package com.example.recipe_research;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class LandingActivityTest {

    @Test
    public void test_lottieAnimation(){
        ActivityScenario.launch(LandingActivity.class);
        onView(withId(R.id.lottieAnimationView)).check(matches(isDisplayed()));
    }

    @Test
    public void test_startButton(){
        ActivityScenario.launch(LandingActivity.class);
        onView(withId(R.id.startButton)).check(matches(isDisplayed()));
        onView(withId(R.id.startButton)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.mainLinearLayout)).check(matches(isDisplayed()));
    }
}
