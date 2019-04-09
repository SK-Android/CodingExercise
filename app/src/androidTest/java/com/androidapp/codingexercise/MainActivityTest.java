package com.androidapp.codingexercise;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.support.test.espresso.Espresso;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


@Rule
public ActivityTestRule<MainActivity> activityRule
        = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchButtonClick(){
        Espresso.onView(ViewMatchers.withId(R.id.action_search))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.action_search))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.isAssignableFrom(EditText.class))
                .perform(ViewActions.typeText("android"));

        Espresso.onView(ViewMatchers.isAssignableFrom(EditText.class))
                .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        Espresso.onView(ViewMatchers.withId(R.id.main_rv))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

//        Espresso.onView(ViewMatchers.withId(R.id.main_rv))
//                .check(ViewAssertions.matches(
//                        ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

//        Espresso.onView(ViewMatchers.withId(R.id.main_rv))
//                .perform(RecyclerViewActions
//                        .actionOnItemAtPosition(1,ViewActions.click()));


    }

}