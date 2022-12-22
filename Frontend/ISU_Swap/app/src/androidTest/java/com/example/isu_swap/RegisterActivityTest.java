package com.example.isu_swap;
import com.example.isu_swap.main.screen.LoginActivity;
import com.example.isu_swap.main.screen.RegisterActivity;
import com.example.isu_swap.main.screen.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityRule = new ActivityTestRule<>(RegisterActivity.class);

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

//    @Test
//    public void testValidRegistration() {
//        // Enter valid Registration information
//        onView(withId(R.id.UsernameTextField)).perform(typeText("Jaydon"), closeSoftKeyboard());
//        onView(withId(R.id.PasswordTextField)).perform(typeText("2022"), closeSoftKeyboard());
//        onView(withId(R.id.EmailTextField)).perform(typeText("test@iastate.edu"), closeSoftKeyboard());
//
//        // Click the Registration button
//        onView(withId(R.id.RegisterButton)).perform(click());
//
//        // Verify that the LoginActivity is launched
//        intended(hasComponent(LoginActivity.class.getName()));
//    }

    @Test
    public void testInvalidPasswordRegistration() {
        // Enter invalid password length
        onView(withId(R.id.UsernameTextField)).perform(typeText("Jaydon"), closeSoftKeyboard());
        onView(withId(R.id.PasswordTextField)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.EmailTextField)).perform(typeText("test@iastate.edu"), closeSoftKeyboard());

        // Click the Registration button
        onView(withId(R.id.RegisterButton)).perform(click());

        // Check invalid
        onView(withId(R.id.attemptsRemainingText)).check(matches(withText("Password must be at least four characters.")));
    }

    @Test
    public void testInvalidEmailRegistration() {
        // Enter invalid email
        onView(withId(R.id.UsernameTextField)).perform(typeText("Jaydon"), closeSoftKeyboard());
        onView(withId(R.id.PasswordTextField)).perform(typeText("2022"), closeSoftKeyboard());
        onView(withId(R.id.EmailTextField)).perform(typeText("test@gmail.com"), closeSoftKeyboard());

        // Click the Registration button
        onView(withId(R.id.RegisterButton)).perform(click());

        // Check invalid
        onView(withId(R.id.attemptsRemainingText)).check(matches(withText("Email must be @iastate.edu")));
    }
    @Test
    public void testLongUserRegistration() {
        // Enter invalid username
        onView(withId(R.id.UsernameTextField)).perform(typeText("Jaydonnnnnnnnnnnnnnnnnnnnnnnnnnn"), closeSoftKeyboard());
        onView(withId(R.id.PasswordTextField)).perform(typeText("2022"), closeSoftKeyboard());
        onView(withId(R.id.EmailTextField)).perform(typeText("test@iastate.edu"), closeSoftKeyboard());

        // Click the Registration button
        onView(withId(R.id.RegisterButton)).perform(click());

        // Check invalid
        onView(withId(R.id.attemptsRemainingText)).check(matches(withText("Username must be less than 20 characters.")));
    }

    @Test
    public void testShortUserRegistration() {
        // Enter invalid username
        onView(withId(R.id.UsernameTextField)).perform(typeText("J"), closeSoftKeyboard());
        onView(withId(R.id.PasswordTextField)).perform(typeText("2022"), closeSoftKeyboard());
        onView(withId(R.id.EmailTextField)).perform(typeText("test@iastate.edu"), closeSoftKeyboard());

        // Click the Registration button
        onView(withId(R.id.RegisterButton)).perform(click());

        // Check invalid
        onView(withId(R.id.attemptsRemainingText)).check(matches(withText("Username must be at least three characters.")));
    }

}
