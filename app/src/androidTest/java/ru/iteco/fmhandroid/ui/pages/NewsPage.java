package ru.iteco.fmhandroid.ui.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import android.app.Activity;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
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
        Allure.step("Проверка отображения главного вида новостей");
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_retry_material_button, WaitAction.TIMEOUT));
    }

    public void checkListNewsOnControlPanel() {
        Allure.step("Проверка списка новостей на панели управления");
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_list_recycler_view, WaitAction.TIMEOUT));
    }

    public void goToControlPanel() {
        Allure.step("Переход на панель управления новостями");
        onView(withId(R.id.edit_news_material_button))
                .perform(click());
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_list_recycler_view, WaitAction.TIMEOUT));
    }

    public void clickSortButton() {
        Allure.step("Нажатие кнопки сортировки");
        onView(withId(R.id.sort_news_material_button))
                .perform(click());
    }

    public void clickFilterButton() {
        Allure.step("Нажатие кнопки фильтрации");
        onView(withId(R.id.filter_news_material_button))
                .perform(click());
    }

    public void clickAddNewsButton() {
        Allure.step("Нажатие кнопки добавления новости");
        onView(withId(R.id.add_news_image_view))
                .perform(click());
    }

    public void clickEditNewsItem(int position) {
        Allure.step("Редактирование новости на позиции " + position);
        onView(ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class))
                .perform(clickRecyclerViewItem(position, R.id.edit_news_item_image_view));
    }

    public void clickDeleteNewsItem(int position) {
        Allure.step("Удаление новости на позиции " + position);
        onView(ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class))
                .perform(clickRecyclerViewItem(position, R.id.delete_news_item_image_view));

        onView(withId(android.R.id.button1))
                .perform(click());
    }

    public void expandNewsItem(int position) {
        Allure.step("Развернуть новость на позиции " + position);
        onView(ViewMatchers.isAssignableFrom(androidx.recyclerview.widget.RecyclerView.class))
                .perform(clickRecyclerViewItem(position, 0));
    }

    public void swipeToRefresh() {
        Allure.step("Обновление списка новостей свайпом");
        onView(withId(R.id.news_control_panel_swipe_to_refresh))
                .perform(click());
    }

    public void verifyNewsInList(String title) {
        Allure.step("Проверка наличия новости в списке с заголовком: " + title);
        // Ждем появления списка новостей
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.news_list_recycler_view, WaitAction.TIMEOUT));
        // Проверяем наличие новости с указанным заголовком
        // Используем allOf для поиска TextView с конкретным ID и текстом
        onView(allOf(withId(R.id.news_item_title_text_view), withText(title)))
                .check(matches(isDisplayed()));
    }

    public void verifyNewsInListAfterCreation(ActivityScenarioRule<AppActivity> activityRule) {
        Allure.step("Проверка наличия новости в списке после создания");
        // Получаем сегодняшнюю дату в формате dd.MM.yyyy (так как selectPublishDate() не меняет дату, выбирается сегодняшняя)
        String today = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        
        // Прокручиваем RecyclerView до конца, чтобы новая новость была видна
        activityRule.getScenario().onActivity(activity -> {
            scrollToEndOfRecyclerView(R.id.news_list_recycler_view, activity);
        });
        
        // Проверяем наличие новости с датой публикации
        onView(allOf(
                withId(R.id.news_item_publication_date_text_view),
                withText(today),
                withParent(withParent(withId(R.id.news_item_material_card_view))),
                isDisplayed()
        )).check(matches(isDisplayed()));
    }

    private void scrollToEndOfRecyclerView(int recyclerViewId, Activity activity) {
        View recyclerView = activity.findViewById(recyclerViewId);
        if (recyclerView instanceof androidx.recyclerview.widget.RecyclerView) {
            androidx.recyclerview.widget.RecyclerView rv = 
                    (androidx.recyclerview.widget.RecyclerView) recyclerView;
            if (rv.getAdapter() != null && rv.getAdapter().getItemCount() > 0) {
                int lastPosition = rv.getAdapter().getItemCount() - 1;
                rv.scrollToPosition(lastPosition);
            }
        }
    }
}
