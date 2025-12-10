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
import ru.iteco.fmhandroid.ui.utils.WaitAction;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
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
    @DisplayName("Тест-кейс 1: Успешный вход с валидными данными")
    public void testSuccessfulLoginWithValidCredentials() {
        loginPage
                .enterLogin("login2")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifySuccessfulLogin();
    }

    @Test
    @DisplayName("Тест-кейс 2: Вход с невалидным логином")
    public void testLoginWithInvalidLogin() {
        loginPage
                .enterLogin("lflkigm")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 3: Вход с невалидным паролем")
    public void testLoginWithInvalidPassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("pass1234")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");

    }

    @Test
    @DisplayName("Тест-кейс 4: Вход с пустым логином")
    public void testLoginWithEmptyLogin() {
        loginPage
                .enterLogin("")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Login and password cannot be empty");
    }

    @Test
    @DisplayName("Тест-кейс 5: Вход с пустым паролем")
    public void testLoginWithEmptyPassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("")
                .clickSignIn();

        loginPage.verifyAlertMessage("Login and password cannot be empty");
    }

    @Test
    @DisplayName("Тест-кейс 6: Вход с пустыми полями логина и пароля")
    public void testLoginWithEmptyFields() {
        loginPage
                .enterLogin("")
                .enterPassword("")
                .clickSignIn();

        loginPage.verifyAlertMessage("Login and password cannot be empty");
    }

    @Test
    @DisplayName("Тест-кейс 7: Ввод пробелов в поле логин")
    public void testLoginWithSpacesInLogin() {
        loginPage
                .enterLogin("   ")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Login and password cannot be empty");
    }

    @Test
    @DisplayName("Тест-кейс 8: Ввод пробелов в поле пароль")
    public void testLoginWithSpacesInPassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("   ")
                .clickSignIn();

        loginPage.verifyAlertMessage("Login and password cannot be empty");
    }

    @Test
    @DisplayName("Тест-кейс 9: Ввод спецсимволов в поле логин")
    public void testLoginWithSpecialCharsInLogin() {
        loginPage
                .enterLogin("!%~*?")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 10: Ввод спецсимволов в поле пароль")
    public void testLoginWithSpecialCharsInPassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("!%~*?")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 11: Ввод 1 символа в поле логин")
    public void testLoginWithOneCharInLogin() {
        loginPage
                .enterLogin("a")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 12: Ввод 1 символа в поле пароль")
    public void testLoginWithOneCharInPassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("1")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 13:Ввод более 1000 символов в поле логин")
    public void testLoginWithLongLogin() {
        String longLogin = new String(new char[1001]).replace("\0", "a");
        loginPage
                .enterLogin(longLogin)
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyLoginFieldLength(1000);
    }

    @Test
    @DisplayName("Тест-кейс 14: Ввод более 1000 символов в поле пароль")
    public void testLoginWithLongPassword() {
        String longPassword = new String(new char[1001]).replace("\0", "a");
        loginPage
                .enterLogin("login2")
                .enterPassword(longPassword)
                .clickSignIn();

        loginPage.verifyPasswordFieldLength(1000);
    }

    @Test
    @DisplayName("Тест-кейс 15: Ввод логина в верхнем регистре")
    public void testLoginWithUppercaseLogin() {
        loginPage
                .enterLogin("LOGIN2")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 16: Ввод пароля в верхнем регистре")
    public void testLoginWithUppercasePassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("PASSWORD2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 17: Ввод кириллицы в поле логин")
    public void testLoginWithCyrillicInLogin() {
        loginPage
                .enterLogin("логин")
                .enterPassword("password2")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 18: Ввод кириллицы в поле пароль")
    public void testLoginWithCyrillicInPassword() {
        loginPage
                .enterLogin("login2")
                .enterPassword("пароль")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 19: Ввод неверного логина и пароля")
    public void testLoginWithInvalidBoth() {
        loginPage
                .enterLogin("wrongLogin")
                .enterPassword("wrongPassword")
                .clickSignIn();

        loginPage.verifyAlertMessage("Something went wrong. Try again later.");
    }

    @Test
    @DisplayName("Тест-кейс 20: Корректное отображение элементов экрана авторизации")
    public void testUIElementsDisplayCorrectly() {
        loginPage.verifyScreenIsDisplayed();
    }
}