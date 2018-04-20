package com.alexsoares.desafiozapvivareal;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.alexsoares.desafiozapvivareal.ui.details.DetailsGameActivity;
import com.alexsoares.desafiozapvivareal.ui.home.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailsActivityTest {
    @Rule
    public ActivityTestRule<DetailsGameActivity> mActivityRule = new ActivityTestRule<>(
            DetailsGameActivity.class);

    @Test
    public void testClickFavorite() {
        try {
            Thread.sleep(4000);
            onView(withId(R.id.swc_favorite_details))
                    .check(matches(isDisplayed()))
                    .perform(click());
            Thread.sleep(4000);


            onView(withId(R.id.imv_icon_toolbar))
                    .check(matches(isDisplayed()))
                    .perform(click());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
