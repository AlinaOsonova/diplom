package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.pages.AboutPage;
import ru.iteco.fmhandroid.ui.pages.LoginPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AboutTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private MainPage mainPage;

    @Before
    public void setUp() {
        try {
            onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.login_text_input_layout, WaitAction.TIMEOUT));
            LoginPage loginPage = new LoginPage();
            loginPage
                    .enterLogin("login2")
                    .enterPassword("password2")
                    .clickSignIn();
            loginPage.verifySuccessfulLogin();
        } catch (RuntimeException ignored) {
        }
        mainPage = new MainPage();
        mainPage.verifyScreenIsDisplayed();
    }

    @Test
    @DisplayName("Тест-кейс 68: Отображение раздела \"About\".")
    public void testAboutPageDisplay() {
        AboutPage aboutPage = mainPage.goToAboutPage();
        aboutPage.verifyScreenIsDisplayed();
    }


    @Test
    @DisplayName("Тест-кейс 70: Отображение версии приложения")
    public void testCheckVersion() {
        AboutPage aboutPage = mainPage.goToAboutPage();
        aboutPage.checkVersion();
    }

    @Test
    @DisplayName("Тест-кейс 71: Отображение ссылки на политику конфиденциальности")
    public void checkUrlPrivacyPolicy() {
        AboutPage aboutPage = mainPage.goToAboutPage();
        aboutPage.checkPrivacyPolicy();
    }

    @Test
    @DisplayName("Тест-кейс 72: Отображение ссылки на правила использования")
    public void checkUrlTermsOfUse() {
        AboutPage aboutPage = mainPage.goToAboutPage();
        aboutPage.checkRules();
    }
}



