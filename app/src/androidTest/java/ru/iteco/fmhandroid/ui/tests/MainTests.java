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
import ru.iteco.fmhandroid.ui.pages.NewsPage;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainTests {

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
    @DisplayName("Тест-кейс 21: Отображение меню навигации")
    public void testNavigationMenuDisplay() {
        mainPage.checkNavigationMenu();
    }

    @Test
    @DisplayName("Тест-кейс 22: Отображение главной страницы")
    public void testMainPageDisplay() {
        mainPage.verifyScreenIsDisplayed();
    }

    @Test
    @DisplayName("Тест-кейс 23: Переход на вкладку \"News\"")
    public void testNavigateToNewsTab() {
        NewsPage newsPage = mainPage.goToNewsPage();
        newsPage.checkMainView();
    }

    @Test
    @DisplayName("Тест-кейс 24: Переход на вкладку \"About\"")
    public void testNavigateToAboutTab() {
        AboutPage aboutPage = mainPage.goToAboutPage();
        aboutPage.verifyScreenIsDisplayed();
    }

    @Test
    @DisplayName("Тест-кейс 26: Пролистывание страницы")
    public void testPageScrolling() {
        mainPage.scrollPage();
        mainPage.verifyScreenIsDisplayed();
    }

    @Test
    @DisplayName("Тест-кейс 27: Обновление страницы")
    public void testPageRefresh() {
        mainPage.refreshPage();
        mainPage.verifyScreenIsDisplayed();
    }

}



