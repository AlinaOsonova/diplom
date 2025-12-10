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
import ru.iteco.fmhandroid.ui.pages.LoginPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;
import ru.iteco.fmhandroid.ui.pages.QuotesPage;
import ru.iteco.fmhandroid.ui.utils.WaitAction;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class QuotesTests {

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

    }

    @Test
    @DisplayName("Тест-кейс 59: Свернуть/развернуть отдельную цитату")
    public void testOpenQuoteCardShowsDescription() {
        QuotesPage quotesPage = mainPage.goToQuotesPage();
        quotesPage.verifyScreenIsDisplayed();

        quotesPage.openFirstQuoteCard();

        quotesPage.verifyQuoteDescriptionIsDisplayed();
    }


}