package com.example.isu_swap;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
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
public class PurchasePageActivityTest {

    @Rule
    public ActivityScenarioRule<LandingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPageActivity.class);

    @Test
    public void purchasePageActivityTest() {
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

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.homepage_recycler),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction cardView = onView(
                allOf(withId(R.id.item_page_buy_button),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                5),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.radio_shipping_meet_seller), withText("Meet Seller"),
                        childAtPosition(
                                allOf(withId(R.id.radiogroup_shipping),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        materialRadioButton.perform(click());

        ViewInteraction materialRadioButton2 = onView(
                allOf(withId(R.id.radio_payment_now), withText("Pay now"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialRadioButton2.perform(click());

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.purchase_item_confirm_purchase),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                9),
                        isDisplayed()));
        cardView2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withClassName(is("com.google.android.material.button.MaterialButton")), withText("No"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                2)));
        materialButton3.perform(scrollTo(), click());
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
