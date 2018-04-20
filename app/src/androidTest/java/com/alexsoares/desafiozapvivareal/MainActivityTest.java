package com.alexsoares.desafiozapvivareal;

import com.alexsoares.desafiozapvivareal.ui.home.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ClipData;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Test
    public void testOne() {
        onView(withId(R.id.rv_games)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000);
            onView(withId(R.id.rv_games))
                    .perform(RecyclerViewActions.scrollToPosition(19));

            Thread.sleep(5000);
            onView(withId(R.id.rv_games))
                    .perform(RecyclerViewActions.scrollToPosition(30));

            Thread.sleep(5000);
            onView(withId(R.id.rv_games))
                    .perform(RecyclerViewActions.scrollToPosition(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testClick() {
        try {
            Thread.sleep(5000);
            onView(withId(R.id.rv_games))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            DetailsActivityTest detailsActivityTest =  new DetailsActivityTest();
            detailsActivityTest.testClickFavorite();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
