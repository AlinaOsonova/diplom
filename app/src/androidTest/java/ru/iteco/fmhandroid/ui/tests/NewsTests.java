package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.NoMatchingViewException;
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
import ru.iteco.fmhandroid.ui.pages.CreateEditNewsPage;
import ru.iteco.fmhandroid.ui.pages.FilterNewsPage;
import ru.iteco.fmhandroid.ui.pages.LoginPage;
import ru.iteco.fmhandroid.ui.pages.MainPage;
import ru.iteco.fmhandroid.ui.pages.NewsPage;
import ru.iteco.fmhandroid.ui.utils.TestConstants;
import ru.iteco.fmhandroid.ui.utils.TestData;
import ru.iteco.fmhandroid.ui.utils.WaitAction;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTests {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private MainPage mainPage;
    private NewsPage newsPage;

    @Before
    public void setUp() {
        try {
            onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.login_text_input_layout, WaitAction.TIMEOUT));
            LoginPage loginPage = new LoginPage();
            loginPage
                    .enterLogin(TestData.getValidLogin())
                    .enterPassword(TestData.getValidPassword())
                    .clickSignIn();
            loginPage.verifySuccessfulLogin();
        } catch (RuntimeException ignored) {
        }
        mainPage = new MainPage();
        newsPage = new NewsPage();
    }

    @Test
    @DisplayName("Тест-кейс 28: Свернуть/развернуть раздел новостей")
    public void testCollapseExpandNewsSection() {
        mainPage.verifyScreenIsDisplayed();
        mainPage.collapseExpandNews();
        mainPage.collapseExpandNews();
    }

    @Test
    @DisplayName("Тест-кейс 29: Переход к полному списку новостей")
    public void testGoToAllNews() {
        mainPage.verifyScreenIsDisplayed();
        mainPage.goToAllNews();
        newsPage.checkMainView();
    }

    @Test
    @DisplayName("Тест-кейс 30: Отображение списка новостей")
    public void testDisplayNewsList() {
        mainPage.goToNewsPage();
        newsPage.checkMainView();
    }

    @Test
    @DisplayName("Тест-кейс 31: Создание новости с валидными данными")
    public void testCreateNewsWithValidData() {
        String newsTitle = TestData.getTestNewsTitle();
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription(TestData.getTestNewsDescription());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 32: Создание новости с пустым заголовком")
    public void testCreateNewsWithEmptyTitle() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterDescription(TestData.getTestNewsDescription());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        createPage.verifyWarningDisplayed();
    }

    @Test
    @DisplayName("Тест-кейс 33: Создание новости с пустым описанием")
    public void testCreateNewsWithEmptyDescription() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(TestData.getTestNewsTitle());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        createPage.verifyWarningDisplayed();
    }

    @Test
    @DisplayName("Тест-кейс 34: Создание новости с латиницей в заголовке")
    public void testCreateNewsWithLatinTitle() {
        String newsTitle = "Test Title Latin";
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription(TestData.getTestNewsDescription());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 35: Создание новости с цифрами в заголовке")
    public void testCreateNewsWithNumbersTitle() {
        String newsTitle = "123456";
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription(TestData.getTestNewsDescription());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 36: Создание новости с кириллицей в заголовке")
    public void testCreateNewsWithCyrillicTitle() {
        String newsTitle = "Тестовый заголовок";
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription(TestData.getTestNewsDescription());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 37: Проверка ограничения на количество символов в заголовке")
    public void testTitleLengthLimit() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            sb.append("a");
        }
        createPage.enterTitle(sb.toString());
        createPage.enterDescription(TestData.getTestNewsDescription());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
    }

    @Test
    @DisplayName("Тест-кейс 38: Создание новости с латиницей в описании")
    public void testCreateNewsWithLatinDescription() {
        String newsTitle = TestData.getTestNewsTitle();
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription("Test Description Latin");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 39: Создание новости с кириллицей в описании")
    public void testCreateNewsWithCyrillicDescription() {
        String newsTitle = TestData.getTestNewsTitle();
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription("Тестовое описание");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 40: Создание новости с цифрами в описании")
    public void testCreateNewsWithNumbersDescription() {
        String newsTitle = TestData.getTestNewsTitle();
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription("123456");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 41: Создание новости со спецсимволами в описании")
    public void testCreateNewsWithSpecialCharsDescription() {
        String newsTitle = TestData.getTestNewsTitle();
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(newsTitle);
        createPage.enterDescription("!@#$%^&*()");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
        newsPage.checkListNewsOnControlPanel();
        newsPage.verifyNewsInListAfterCreation(activityRule);
    }

    @Test
    @DisplayName("Тест-кейс 42: Проверка ограничения на количество символов в описании")
    public void testDescriptionLengthLimit() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterCategory(TestData.getTestNewsCategory());
        createPage.enterTitle(TestData.getTestNewsTitle());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            sb.append("a");
        }
        createPage.enterDescription(sb.toString());
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        createPage.selectPublishDate();
        createPage.selectPublishTime();
        createPage.clickSaveButton();
    }


    @Test
    @DisplayName("Тест-кейс 44: Отмена создания новости")
    public void testCancelNewsCreation() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickAddNewsButton();
        CreateEditNewsPage createPage = new CreateEditNewsPage();
        createPage.enterTitle(TestData.getTestNewsTitle());
        createPage.clickCancelButton();
    }

    @Test
    @DisplayName("Тест-кейс 45: Редактирование существующей новости")
    public void testEditNews() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.checkListNewsOnControlPanel();
        newsPage.clickEditNewsItem(0);
        CreateEditNewsPage editPage = new CreateEditNewsPage();
        editPage.enterTitle("Updated Title");
        editPage.clickSaveButton();
    }

    @Test
    @DisplayName("Тест-кейс 46: Удаление новости")
    public void testDeleteNews() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.checkListNewsOnControlPanel();
        newsPage.clickDeleteNewsItem(0);
    }

    @Test
    @DisplayName("Тест-кейс 47: Фильтрация новостей по категориям и датам")
    public void testFilterNewsByCategoryAndDate() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickFilterButton();
        FilterNewsPage filterPage = new FilterNewsPage();
        filterPage.selectCategory(TestData.getTestNewsCategory());
        filterPage.clickFilterButton();
    }

    @Test
    @DisplayName("Тест-кейс 49: Сброс фильтрации")
    public void testResetFilter() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickFilterButton();
        FilterNewsPage filterPage = new FilterNewsPage();
        filterPage.clickCancelButton();
    }

    @Test
    @DisplayName("Тест-кейс 51: Свайп для обновления списка новостей")
    public void testSwipeToRefresh() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.swipeToRefresh();
    }

    @Test
    @DisplayName("Тест-кейс 54: Сортировка новостей")
    public void testSortNews() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.clickSortButton();
    }

    @Test
    @DisplayName("Тест-кейс 55: Свернуть/развернуть отдельную новость")
    public void testExpandCollapseNewsItem() {
        mainPage.goToNewsPage();
        newsPage.goToControlPanel();
        newsPage.checkListNewsOnControlPanel();
        newsPage.expandNewsItem(0);
        newsPage.expandNewsItem(0);
    }
}
