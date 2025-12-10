package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import org.hamcrest.Matcher;

import java.util.Collection;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

public class LoginPage {

    private static final String INPUT_LOGIN = "Login";
    private static final String INPUT_PASSWORD = "Password";
    private static final Integer SUBMIT_BUTTON_SIGN_IN = R.id.enter_button;

    public static ViewAction withTextLengthLessThanOrEqualTo(final int maxLength) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "check text length <= " + maxLength;
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view;
                CharSequence text = tv.getText();
                if (text != null && text.length() > maxLength) {
                }
            }
        };
    }

    public void verifyScreenIsDisplayed() {
        onView(withHint(INPUT_LOGIN)).check(matches(isDisplayed()));
        onView(withHint(INPUT_PASSWORD)).check(matches(isDisplayed()));
        onView(withId(SUBMIT_BUTTON_SIGN_IN)).check(matches(isDisplayed()));
    }

    public LoginPage enterLogin(String login) {
        onView(withHint(INPUT_LOGIN))
                .perform(click(), replaceText(login), closeSoftKeyboard());
        return this;
    }

    public LoginPage enterPassword(String password) {
        onView(withHint(INPUT_PASSWORD))
                .perform(click(), replaceText(password), closeSoftKeyboard());
        return this;
    }

    public void clickSignIn() {
        onView(withId(SUBMIT_BUTTON_SIGN_IN)).perform(click());
    }

    public void verifySuccessfulLogin() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.container_list_news_include_on_fragment_main, WaitAction.TIMEOUT));
        onView(withId(R.id.container_list_news_include_on_fragment_main)).check(matches(isDisplayed()));
    }

    public void verifyAlertMessage(String expectedMessage) {
        onView(withText(expectedMessage))
                .inRoot(withDecorView(not(is(getCurrentActivityDecorView()))))
                .check(matches(isDisplayed()));
    }

    private View getCurrentActivityDecorView() {
        final Activity[] activity = new Activity[1];
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> activities = ActivityLifecycleMonitorRegistry
                    .getInstance()
                    .getActivitiesInStage(Stage.RESUMED);
            if (!activities.isEmpty()) {
                activity[0] = activities.iterator().next();
            }
        });
        return activity[0].getWindow().getDecorView();
    }

    public void verifyLoginFieldLength(int maxLength) {
        onView(withHint("Login"))
                .perform(withTextLengthLessThanOrEqualTo(maxLength));
    }

    public void verifyPasswordFieldLength(int maxLength) {
        onView(withHint("Password"))
                .perform(withTextLengthLessThanOrEqualTo(maxLength));
    }

}