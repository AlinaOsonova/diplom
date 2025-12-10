package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import ru.iteco.fmhandroid.R;

public class CreateEditNewsPage {

    public void enterCategory(String category) {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .perform(replaceText(category), closeSoftKeyboard());
    }

    public void enterTitle(String title) {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .perform(replaceText(title), closeSoftKeyboard());
    }

    public void enterDescription(String description) {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .perform(replaceText(description), closeSoftKeyboard());
    }

    public void selectPublishDate() {
        onView(allOf(withId(R.id.news_item_publish_date_text_input_edit_text), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click());
    }

    public void selectPublishTime() {
        onView(allOf(withId(R.id.news_item_publish_time_text_input_edit_text), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click());
    }

    public void clickSaveButton() {
        onView(allOf(withId(R.id.save_button), withText("Save"), withContentDescription("Save")))
                .perform(scrollTo(), click());
    }

    public void clickCancelButton() {
        onView(withId(R.id.cancel_button))
                .perform(click());
    }

    public void verifyWarningDisplayed() {
        onView(isRoot()).perform(ru.iteco.fmhandroid.ui.utils.WaitAction.waitDisplayed(
                R.id.news_item_title_text_input_layout, 5000));
    }
}
