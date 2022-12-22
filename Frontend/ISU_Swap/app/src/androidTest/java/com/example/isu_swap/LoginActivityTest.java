package com.example.isu_swap;

import com.example.isu_swap.main.screen.HomepageActivity;
import com.example.isu_swap.main.screen.LoginActivity;
import com.example.isu_swap.main.screen.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
//import androidx.test.rule.ActivityTestRule;
//import android.support.test.rule.ActivityTestRule;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

//test
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        // Initialize Espresso Intents
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Espresso Intents
        Intents.release();
    }

    @Test
    public void testValidLogin() {
        // Enter valid login information
        onView(withId(R.id.UsernameTextField)).perform(typeText("jdn"), closeSoftKeyboard());
        onView(withId(R.id.PasswordTextField)).perform(typeText("0000"), closeSoftKeyboard());

        // Click the login button
        onView(withId(R.id.LoginButton)).perform(click());

        // Verify that the HomepageActivity is launched
        intended(hasComponent(HomepageActivity.class.getName()));
    }

    @Test
    public void testInvalidLogin() {
        // Enter invalid login information
        onView(withId(R.id.UsernameTextField)).perform(typeText("jdn"), closeSoftKeyboard());
        onView(withId(R.id.PasswordTextField)).perform(typeText("1111"), closeSoftKeyboard());

        // Click the login button
        onView(withId(R.id.LoginButton)).perform(click());

        // Verify that an error message is displayed
        onView(withId(R.id.error_text)).check(matches(withText("Password is incorrect.")));
    }
}
