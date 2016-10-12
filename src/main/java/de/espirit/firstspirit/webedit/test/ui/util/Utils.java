package de.espirit.firstspirit.webedit.test.ui.util;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class Utils {
    /**
     * After processing idle time for a lot of HTTP operations.
     */
    private static final int WAIT = 250;

    public static String env(final String name, final String defValue) {
        final String env = System.getenv(name);
        if (env != null) {
            return env;
        }

        return System.getProperty(name, defValue);
    }

    /**
     * Finds element by the given condition
     *
     * @param webDriver The webdriver instance
     * @param by The condition
     * @return Returns the web element if found
     */
    public static WebElement findElement(@NotNull final WebDriver webDriver, @NotNull final By by) throws CCAPIException {
        try {
            return webDriver.findElement(by);
        } catch (WebDriverException exception){
            throw new CCAPIException(exception.getMessage(), webDriver);
        }
    }

    /**
     * Finds a number of elements by the given condition
     *
     * @param webDriver The webdriver instance
     * @param by The condition
     * @return Returns a number of web elements if found
     */
    public static List<WebElement> findElements(@NotNull final WebDriver webDriver, @NotNull final By by) throws CCAPIException {
        try {
            return webDriver.findElements(by);
        } catch (WebDriverException exception){
            throw new CCAPIException(exception.getMessage(), webDriver);
        }
    }

    /**
     * Finds a web element by the given condition
     *
     * @param webDriver The webdriver instance
     * @param expectedCondition The condition
     * @return Returns the web element if found
     */
    public static WebElement find(@NotNull final WebDriver webDriver, @NotNull final ExpectedCondition<WebElement> expectedCondition) throws CCAPIException {
        try {
            return new WebDriverWait(webDriver, Constants.WEBDRIVER_WAIT).until(expectedCondition);
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }
    }

    /**
     * Finds a element in the web element by the given condition
     *
     * @param webDriver The webdriver instance
     * @param webElement The webelement
     * @param by The condition
     * @return Returns the web element if found
     */
    public static WebElement findItemInElement(@NotNull final WebDriver webDriver, @NotNull final WebElement webElement, @NotNull final By by) throws CCAPIException {
        try {
            return webElement.findElement(by);
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }
    }

    /**
     * Finds a number of elements in the web element by the given condition
     *
     * @param webDriver The webdriver instance
     * @param webElement The webelement
     * @param by The condition
     * @return Returns a number of web elements if found
     */
    public static List<WebElement> findMultipleItemsInElement(@NotNull final WebDriver webDriver, @NotNull final WebElement webElement, @NotNull final By by) throws CCAPIException {
        try {
            return webElement.findElements(by);
        } catch (WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }
    }

    /**
     * Sleeps {@link #WAIT} milliseconds.
     */
    public static void idle() {
        try {
            Thread.sleep(WAIT); // let it load
        } catch (final InterruptedException e) {
            throw new RuntimeException("waiting interrupted!", e);
        }
    }

    /**
     * Waits for the CC to be fully loaded
     *
     * @param webDriver the webdriver to use
     */
    public static void waitForCC(@NotNull final WebDriver webDriver) {
        new WebDriverWait(webDriver, 20).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver d) {

                boolean weApiAvailable = ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API !== 'undefined'").equals(Boolean.TRUE);

                if(weApiAvailable && ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API.Common.getPreviewElement() !== 'undefined'").equals(Boolean.TRUE))
                {
                    WebElement previewFrame = d.findElement(By.id("previewContent"));
                    webDriver.switchTo().frame(previewFrame);
                    boolean previewState = ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete");
                    webDriver.switchTo().defaultContent();
                    return previewState;
                }

                return false;
            }
        });
    }

    /**
     * Waits for the CC to load a specified page
     *
     * @param webDriver the webdriver to use
     */
    public static void waitForPage(@NotNull final WebDriver webDriver, @NotNull final long idToWaitFor) {
        new WebDriverWait(webDriver, 20).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver d) {

                boolean weApiAvailable = ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API !== 'undefined'").equals(Boolean.TRUE);

                if(weApiAvailable && ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API.Common.getPreviewElement() !== 'undefined'").equals(Boolean.TRUE))
                {
                    WebElement previewFrame = d.findElement(By.id("previewContent"));
                    webDriver.switchTo().frame(previewFrame);
                    boolean previewState = ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete");
                    webDriver.switchTo().defaultContent();

                    if(previewState && Objects.equals(((JavascriptExecutor) d).executeScript("return WE_API.Common.getPreviewElement().getId()"), idToWaitFor))
                        return true;
                }

                return false;
            }
        });
    }


}
