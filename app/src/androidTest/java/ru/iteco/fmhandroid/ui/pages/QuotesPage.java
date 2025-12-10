package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

public class QuotesPage {

    private final ViewInteraction pageTitle = onView(withId(R.id.our_mission_title_text_view));
    private final ViewInteraction quotesList = onView(withId(R.id.our_mission_item_list_recycler_view));

    public QuotesPage verifyScreenIsDisplayed() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.our_mission_title_text_view, WaitAction.TIMEOUT));
        pageTitle.check(matches(isDisplayed()));
        quotesList.check(matches(isDisplayed()));
        return this;
    }

    public QuotesPage openFirstQuoteCard() {
        quotesList.perform(actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.our_mission_item_description_text_view, WaitAction.TIMEOUT_SHORT));
        return this;
    }

    public QuotesPage verifyQuoteDescriptionIsDisplayed() {
        quotesList.perform(actionOnItemAtPosition(0, new ViewAction() {
            @Override
            public String getDescription() {
                return "check description is displayed";
            }

            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public void perform(UiController uiController, View view) {
                View descriptionView = view.findViewById(R.id.our_mission_item_description_text_view);
                if (descriptionView != null && descriptionView.getVisibility() == View.VISIBLE) {
                } else {
                    throw new AssertionError("Quote description is not visible");
                }
            }
        }));
        return this;
    }

}
