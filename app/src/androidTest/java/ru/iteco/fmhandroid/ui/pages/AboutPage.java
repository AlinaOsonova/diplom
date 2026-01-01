package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

public class AboutPage {

    public void verifyScreenIsDisplayed() {
        Allure.step("Проверка отображения страницы About");
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.about_version_title_text_view, WaitAction.TIMEOUT));
    }

    public void checkVersion() {
        Allure.step("Проверка версии приложения");
        onView(withId(R.id.about_version_value_text_view)).check(matches(withText("1.0.0")));
    }

    public void checkPrivacyPolicy() {
        Allure.step("Проверка ссылки на политику конфиденциальности");
        onView(withId(R.id.about_privacy_policy_value_text_view))
                .check(matches(withText("https://vhospice.org/#/privacy-policy/")));
    }

    public void checkRules() {
        Allure.step("Проверка ссылки на правила использования");
        onView(withId(R.id.about_terms_of_use_value_text_view))
                .check(matches(withText("https://vhospice.org/#/terms-of-use")));
    }
}

