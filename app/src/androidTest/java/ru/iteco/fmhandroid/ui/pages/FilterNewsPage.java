package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class FilterNewsPage {

    public void selectCategory(String category) {
        Allure.step("Выбор категории для фильтра: " + category);
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .perform(replaceText(category), closeSoftKeyboard());
    }

    public void clickFilterButton() {
        Allure.step("Нажатие кнопки фильтрации");
        onView(withId(R.id.filter_button))
                .perform(click());
    }

    public void clickCancelButton() {
        Allure.step("Нажатие кнопки отмены фильтрации");
        onView(withId(R.id.cancel_button))
                .perform(click());
    }
}
