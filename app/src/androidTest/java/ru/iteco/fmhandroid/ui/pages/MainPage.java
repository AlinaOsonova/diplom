package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

public class MainPage {

    public static void logout() {
        onView(withId(R.id.authorization_image_button)).perform(click());
        onView(withText("Log out")).perform(click());
    }

    public void verifyScreenIsDisplayed() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.container_list_news_include_on_fragment_main, WaitAction.TIMEOUT));
    }

    public void checkNavigationMenu() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText("About")).check(matches(isDisplayed()));
    }

    public NewsPage goToNewsPage() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText("News")).perform(click());

        return new NewsPage();
    }

    public AboutPage goToAboutPage() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText("About")).perform(click());

        return new AboutPage();
    }

    public void scrollPage() {
        onView(isRoot()).perform(swipeDown());
    }

    public void refreshPage() {
        onView(isRoot()).perform(swipeDown());
    }

    public QuotesPage goToQuotesPage() {
        onView(withId(R.id.our_mission_image_button)).perform(click());
        return new QuotesPage();
    }

    public void collapseExpandNews() {
        onView(withId(R.id.expand_material_button)).perform(click());
    }

    public void goToAllNews() {
        onView(withId(R.id.all_news_text_view)).perform(click());
    }
}