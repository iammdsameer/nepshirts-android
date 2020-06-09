package com.nepshirts.android.user;

import androidx.test.annotation.UiThreadTest;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.nepshirts.android.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class RegisterActivityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> loginActivityTest = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    private String fullName="Bikash Pandey";
    private String uphone ="9841576963";
    private String ugender = "Male";
    private String ubirthday = "2020/15/50";
    private String upassword1 = "123456";
    private String upassword2 = "123456";
    private  String uemail = "bikpan@bikash.com";


    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void registerUserTest(){

        //input text
        Espresso.onView(withId(R.id.name)).perform(typeText(fullName)).check(matches( withText(fullName)));
//        Espresso.onView(withId(R.id.email)).perform(click());
        Espresso.onView(withId(R.id.email)).perform(replaceText(uemail)).check(matches( withText(uemail)));

     //   Espresso.onView(withId(R.id.password_input)).perform(typeText(password)).check(matches(withText(password)));



        Espresso.onView(withId(R.id.phone)).perform(replaceText(uphone)).check(matches( withText(uphone)));
        Espresso.onView(withId(R.id.gender)).perform(replaceText(ugender)).check(matches( withText(ugender)));
        Espresso.onView(withId(R.id.birthday)).perform(replaceText(ubirthday)).check(matches( withText(ubirthday)));
        Espresso.onView(withId(R.id.password1)).perform(replaceText(upassword1)).check(matches(withText(upassword1)));
        Espresso.onView(withId(R.id.password2)).perform(replaceText(upassword2)).check(matches(withText(upassword2)));


        //close keyboard
        Espresso.closeSoftKeyboard();
        //perform button click
        Espresso.onView(withId(R.id.reg_button)).perform(click());
        //test returned text



    }

    @After
    public void tearDown() throws Exception {
    }
}