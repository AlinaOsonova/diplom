package ru.iteco.fmhandroid.ui.utils;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Matcher;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeoutException;

public class WaitAction {

    public static final Integer TIMEOUT = 10000;
    public static final Integer TIMEOUT_SHORT = 5000;

    public static ViewAction waitDisplayed(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> has been displayed during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                // #region agent log
                try {
                    String logPath = "c:\\Users\\Alina\\AndroidStudioProjects\\diplom\\.cursor\\debug.log";
                    String logEntry = String.format("{\"sessionId\":\"debug-session\",\"runId\":\"run1\",\"hypothesisId\":\"B,D\",\"location\":\"WaitAction.java:37\",\"message\":\"waitDisplayed perform() entry\",\"data\":{\"viewId\":%d,\"timeout\":%d},\"timestamp\":%d}\n", viewId, millis, System.currentTimeMillis());
                    Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (Exception e) {}
                // #endregion
                
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> matchId = withId(viewId);
                final Matcher<View> matchDisplayed = isDisplayed();
                
                int iterationCount = 0;

                do {
                    iterationCount++;
                    int matchingViews = 0;
                    int displayedViews = 0;
                    
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child)) {
                            matchingViews++;
                            if (matchDisplayed.matches(child)) {
                                displayedViews++;
                                // #region agent log
                                try {
                                    String logPath = "c:\\Users\\Alina\\AndroidStudioProjects\\diplom\\.cursor\\debug.log";
                                    String logEntry = String.format("{\"sessionId\":\"debug-session\",\"runId\":\"run1\",\"hypothesisId\":\"B\",\"location\":\"WaitAction.java:52\",\"message\":\"View found and displayed\",\"data\":{\"viewId\":%d,\"iteration\":%d,\"elapsed\":%d},\"timestamp\":%d}\n", viewId, iterationCount, System.currentTimeMillis() - startTime, System.currentTimeMillis());
                                    Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                                } catch (Exception e) {}
                                // #endregion
                                return;
                            }
                        }
                    }
                    
                    // #region agent log
                    if (iterationCount % 20 == 0) {
                        try {
                            String logPath = "c:\\Users\\Alina\\AndroidStudioProjects\\diplom\\.cursor\\debug.log";
                            String logEntry = String.format("{\"sessionId\":\"debug-session\",\"runId\":\"run1\",\"hypothesisId\":\"B,D\",\"location\":\"WaitAction.java:65\",\"message\":\"Still waiting for view\",\"data\":{\"viewId\":%d,\"iteration\":%d,\"matchingViews\":%d,\"displayedViews\":%d,\"elapsed\":%d},\"timestamp\":%d}\n", viewId, iterationCount, matchingViews, displayedViews, System.currentTimeMillis() - startTime, System.currentTimeMillis());
                            Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        } catch (Exception e) {}
                    }
                    // #endregion

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // #region agent log
                try {
                    String logPath = "c:\\Users\\Alina\\AndroidStudioProjects\\diplom\\.cursor\\debug.log";
                    String logEntry = String.format("{\"sessionId\":\"debug-session\",\"runId\":\"run1\",\"hypothesisId\":\"B,D\",\"location\":\"WaitAction.java:75\",\"message\":\"Timeout - view not found\",\"data\":{\"viewId\":%d,\"iterations\":%d,\"elapsed\":%d},\"timestamp\":%d}\n", viewId, iterationCount, System.currentTimeMillis() - startTime, System.currentTimeMillis());
                    Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (Exception e) {}
                // #endregion

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

}