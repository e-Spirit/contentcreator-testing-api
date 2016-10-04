package de.espirit.firstspirit.webedit.test.ui.util;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
     * @param driver The webdriver instance
     * @param by     The condition
     * @return Returns the web element if found
     */
    public static WebElement find(@NotNull final WebDriver driver, @NotNull final By by) {
        idle();
        return driver.findElement(by);
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
}
