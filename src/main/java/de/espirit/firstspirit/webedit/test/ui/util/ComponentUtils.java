package de.espirit.firstspirit.webedit.test.ui.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class ComponentUtils {
    private static final Class<ComponentUtils> LOGGER = ComponentUtils.class;

    public static boolean hasElement(WebElement webElement, WebDriver webDriver, By by) {
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            webElement.findElement(by);
            return true;
        } catch(NoSuchElementException exception) {
            return false;
        } finally {
            webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
    }

}
