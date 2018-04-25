package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.hamcrest.CoreMatchers;
import org.hamcrest.text.IsEmptyString;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Nono on 4/24/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EndPointAsyncTaskIdlingResourceTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void jokeTextViewIsNotEmpty() {
        //click the tellJoke Button
        Espresso.onView(ViewMatchers.withId(R.id.tell_joke)).perform(ViewActions.click());

        //check if the jokeTextView is not empty
        Espresso.onView(ViewMatchers.withId(R.id.joke_view))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))));

//        Log.d("JokeTest", ((EndPointIdlingResource)mIdlingResource).getJoke());
//        ViewMatchers.withText("kk").matches(CoreMatchers.not(ViewMatchers.withText("")));
//                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))));

//        ((EndPointIdlingResource)mIdlingResource).getJoke()
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
