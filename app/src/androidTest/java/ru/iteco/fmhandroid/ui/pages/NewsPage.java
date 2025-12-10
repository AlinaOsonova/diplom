package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

public class NewsPage {

    private ViewAction clickRecyclerViewItem(final int position, final int buttonId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "click item " + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                androidx.recyclerview.widget.RecyclerView rv =
                        (androidx.recyclerview.widget.RecyclerView) view;
                rv.scrollToPosition(position);
                uiController.loopMainThreadUntilIdle();
                androidx.recyclerview.widget.RecyclerView.ViewHolder vh =
                        rv.findViewHolderForAdapterPosition(position);
                if (vh != null) {
                    View button = buttonId != 0 ? vh.itemView.findViewById(buttonId) : vh.itemView;
                    if (button != null && button.isClickable()) {
                        button.performClick();
                    }
                }
            }
        };
    }

    public void checkMainView() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_retry_material_button, WaitAction.TIMEOUT));
    }

    public void checkListNewsOnControlPanel() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_list_recycler_view, WaitAction.TIMEOUT));
    }

    public void goToControlPanel() {
        onView(withId(R.id.edit_news_material_button))
                .perform(click());
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_list_recycler_view, WaitAction.TIMEOUT));
    }

    public void clickSortButton() {
        onView(withId(R.id.sort_news_material_button))
                .perform(click());
    }

    public void clickFilterButton() {
        onView(withId(R.id.filter_news_material_button))
                .perform(click());
    }

    public void clickAddNewsButton() {
        onView(withId(R.id.add_news_image_view))
                .perform(click());
    }

    public void clickEditNewsItem(int position) {
        onView(ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class))
                .perform(clickRecyclerViewItem(position, R.id.edit_news_item_image_view));
    }

    public void clickDeleteNewsItem(int position) {
        onView(ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class))
                .perform(clickRecyclerViewItem(position, R.id.delete_news_item_image_view));

        onView(withId(android.R.id.button1))
                .perform(click());
    }

    public void expandNewsItem(int position) {
        onView(ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class))
                .perform(clickRecyclerViewItem(position, 0));
    }

    public void swipeToRefresh() {
        onView(withId(R.id.news_control_panel_swipe_to_refresh))
                .perform(click());
    }
}
