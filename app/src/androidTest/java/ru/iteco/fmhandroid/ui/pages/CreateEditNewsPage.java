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

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.TestConstants;

public class CreateEditNewsPage {

    public void enterCategory(String category) {
        Allure.step("Ввод категории новости: " + category);
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .perform(replaceText(category), closeSoftKeyboard());
    }

    public void enterTitle(String title) {
        Allure.step("Ввод заголовка новости: " + title);
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .perform(replaceText(title), closeSoftKeyboard());
    }

    public void enterDescription(String description) {
        Allure.step("Ввод описания новости: " + description);
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .perform(replaceText(description), closeSoftKeyboard());
    }

    public void selectPublishDate() {
        Allure.step("Выбор даты публикации");
        onView(allOf(withId(R.id.news_item_publish_date_text_input_edit_text), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText(TestConstants.UI_TEXT_OK))).perform(scrollTo(), click());
    }

    public void selectPublishTime() {
        Allure.step("Выбор времени публикации");
        onView(allOf(withId(R.id.news_item_publish_time_text_input_edit_text), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.button1), withText(TestConstants.UI_TEXT_OK))).perform(scrollTo(), click());
    }

    public void clickSaveButton() {
        Allure.step("Нажатие кнопки сохранения");
        onView(allOf(withId(R.id.save_button), withText(TestConstants.UI_TEXT_SAVE), withContentDescription(TestConstants.UI_TEXT_SAVE)))
                .perform(scrollTo(), click());
    }

    public void clickCancelButton() {
        Allure.step("Нажатие кнопки отмены");
        onView(withId(R.id.cancel_button))
                .perform(click());
    }

    public void verifyWarningDisplayed() {
        Allure.step("Проверка отображения предупреждения");
        onView(isRoot()).perform(ru.iteco.fmhandroid.ui.utils.WaitAction.waitDisplayed(
                R.id.news_item_title_text_input_layout, 5000));
    }
}
