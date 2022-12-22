package com.example.isu_swap;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.isu_swap.main.screen.LandingPageActivity;
import com.example.isu_swap.main.screen.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InboxActivityTest {

    @Rule
    public ActivityScenarioRule<LandingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPageActivity.class);

    @Test
    public void inboxActivityTest() {
        ViewInteraction materialButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.button_login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.UsernameTextField),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("jomedman"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.PasswordTextField),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("1577"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.LoginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(withText("Inbox")).perform(click());


        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.compose_button), withText("Compose"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.to_text_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("jdn"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.subject_text_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Espresso Test Message"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.body_text_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.ContentFrameLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Espressp Test Message Body"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.app_bar_send), withContentDescription("Send"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.app_bar_send),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        button.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
