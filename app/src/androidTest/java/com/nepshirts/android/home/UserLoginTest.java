package com.nepshirts.android.home;

import android.app.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.EspressoException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import com.nepshirts.android.R;
import com.nepshirts.android.user.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class UserLoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTest = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private  String email = "hari@hari.com";
    private String password = "123456";

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void loginTest() throws InterruptedException {
        //input text

        Espresso.onView(withId(R.id.email_input)).perform(typeText(email)).check(matches( withText(email)));

        Espresso.onView(withId(R.id.password_input)).perform(typeText(password)).check(matches(withText(password)));

        //close keyboard
        Espresso.closeSoftKeyboard();
        //perform button click
        Espresso.onView(withId(R.id.login_btn)).perform(click());
        //test returned text


    }

    @After
    public void tearDown() throws Exception {
    }
}