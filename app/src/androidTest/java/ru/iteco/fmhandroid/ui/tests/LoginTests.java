package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.pages.LoginPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;
import ru.iteco.fmhandroid.ui.utils.TestConstants;
import ru.iteco.fmhandroid.ui.utils.TestData;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private LoginPage loginPage;

    @Before
    public void setUp() {

        loginPage = new LoginPage();

        try {
            onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.login_text_input_layout, WaitAction.TIMEOUT));
        } catch (RuntimeException exception) {
            MainPage.logout();
        }
    }

    @Test
    public void testSuccessfulLoginWithValidCredentials() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifySuccessfulLogin();
    }

    @Test
    public void testLoginWithInvalidLogin() {
        loginPage
                .enterLogin("lflkigm")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithInvalidPassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("pass1234")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);

    }

    @Test
    public void testLoginWithEmptyLogin() {
        loginPage
                .enterLogin("")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_LOGIN_PASSWORD_EMPTY);
    }

    @Test
    public void testLoginWithEmptyPassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_LOGIN_PASSWORD_EMPTY);
    }

    @Test
    public void testLoginWithEmptyFields() {
        loginPage
                .enterLogin("")
                .enterPassword("")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_LOGIN_PASSWORD_EMPTY);
    }

    @Test
    public void testLoginWithSpacesInLogin() {
        loginPage
                .enterLogin("   ")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_LOGIN_PASSWORD_EMPTY);
    }

    @Test
    public void testLoginWithSpacesInPassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("   ")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_LOGIN_PASSWORD_EMPTY);
    }

    @Test
    public void testLoginWithSpecialCharsInLogin() {
        loginPage
                .enterLogin("!%~*?")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithSpecialCharsInPassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("!%~*?")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithOneCharInLogin() {
        loginPage
                .enterLogin("a")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithOneCharInPassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("1")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithLongLogin() {
        // Замена "a".repeat(1001)
        String longLogin = new String(new char[1001]).replace("\0", "a");
        loginPage
                .enterLogin(longLogin)
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        // Используем публичный метод из LoginPage
        loginPage.verifyLoginFieldLength(1000);
    }

    @Test
    public void testLoginWithLongPassword() {
        // Замена "a".repeat(1001)
        String longPassword = new String(new char[1001]).replace("\0", "a");
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword(longPassword)
                .clickSignIn();

        loginPage.verifyPasswordFieldLength(1000);
    }

    @Test
    public void testLoginWithUppercaseLogin() {
        loginPage
                .enterLogin("LOGIN2")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithUppercasePassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("PASSWORD2")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithCyrillicInLogin() {
        loginPage
                .enterLogin("логин")
                .enterPassword(TestData.getValidPassword())
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithCyrillicInPassword() {
        loginPage
                .enterLogin(TestData.getValidLogin())
                .enterPassword("пароль")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testLoginWithInvalidBoth() {
        loginPage
                .enterLogin("wrongLogin")
                .enterPassword("wrongPassword")
                .clickSignIn();

        loginPage.verifyAlertMessage(TestConstants.ERROR_SOMETHING_WENT_WRONG);
    }

    @Test
    public void testUIElementsDisplayCorrectly() {
        loginPage.verifyScreenIsDisplayed();
    }
}