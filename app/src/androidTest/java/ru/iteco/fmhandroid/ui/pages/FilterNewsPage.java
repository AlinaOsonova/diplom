package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import ru.iteco.fmhandroid.R;

public class FilterNewsPage {

    public void selectCategory(String category) {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .perform(replaceText(category), closeSoftKeyboard());
    }

    public void clickFilterButton() {
        onView(withId(R.id.filter_button))
                .perform(click());
    }

    public void clickCancelButton() {
        onView(withId(R.id.cancel_button))
                .perform(click());
    }
}
