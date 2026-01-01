package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.TestConstants;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

import android.util.Log;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MainPage {

    public static void logout() {
        Allure.step("Выход из системы");
        // #region agent log
        Log.d("DEBUG", "MainPage.logout() entry");
        // #endregion
        
        // Проверяем, видна ли кнопка авторизации (пользователь должен быть авторизован)
        try {
            // #region agent log
            Log.d("DEBUG", "Checking if authorization button is visible");
            // #endregion
            
            try {
                onView(withId(R.id.authorization_image_button)).check(matches(isDisplayed()));
                
                // #region agent log
                Log.d("DEBUG", "Authorization button is visible - performing logout");
                // #endregion
                
                onView(withId(R.id.authorization_image_button)).perform(click());
                onView(withText(TestConstants.UI_TEXT_LOG_OUT)).perform(click());
                
                // #region agent log
                Log.d("DEBUG", "Logout completed successfully");
                // #endregion
            } catch (NoMatchingViewException e) {
                // #region agent log
                Log.d("DEBUG", "NoMatchingViewException - button not found: " + e.getMessage());
                // #endregion
                return;
            } catch (PerformException e) {
                // #region agent log
                Log.d("DEBUG", "PerformException - button not visible: " + e.getMessage());
                // #endregion
                return;
            } catch (RuntimeException e) {
                // #region agent log
                Log.d("DEBUG", "RuntimeException - button check failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                // #endregion
                return;
            }
        } catch (Throwable e) {
            // #region agent log
            Log.d("DEBUG", "Throwable caught - unexpected error: " + e.getClass().getSimpleName() + " - " + (e.getMessage() != null ? e.getMessage() : "no message"));
            // #endregion
            // Пользователь уже не авторизован или на экране логина - ничего не делаем
            return;
        }
    }

    public void verifyScreenIsDisplayed() {
        Allure.step("Проверка отображения главной страницы");
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.container_list_news_include_on_fragment_main, WaitAction.TIMEOUT));
    }

    public void checkNavigationMenu() {
        Allure.step("Проверка меню навигации");
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(TestConstants.UI_TEXT_ABOUT)).check(matches(isDisplayed()));
    }

    public NewsPage goToNewsPage() {
        Allure.step("Переход на страницу новостей");
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(TestConstants.UI_TEXT_NEWS)).perform(click());
        return new NewsPage();
    }

    public AboutPage goToAboutPage() {
        Allure.step("Переход на страницу About");
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(TestConstants.UI_TEXT_ABOUT)).perform(click());
        return new AboutPage();
    }

    public void scrollPage() {
        Allure.step("Прокрутка страницы");
        onView(isRoot()).perform(swipeDown());
    }

    public void refreshPage() {
        Allure.step("Обновление страницы");
        onView(isRoot()).perform(swipeDown());
    }

    public QuotesPage goToQuotesPage() {
        Allure.step("Переход на страницу цитат");
        onView(withId(R.id.our_mission_image_button)).perform(click());
        return new QuotesPage();
    }

    public void collapseExpandNews() {
        Allure.step("Свернуть/развернуть новости");
        onView(withId(R.id.expand_material_button)).perform(click());
    }

    public void goToAllNews() {
        Allure.step("Переход ко всем новостям");
        onView(withId(R.id.all_news_text_view)).perform(click());
    }
}