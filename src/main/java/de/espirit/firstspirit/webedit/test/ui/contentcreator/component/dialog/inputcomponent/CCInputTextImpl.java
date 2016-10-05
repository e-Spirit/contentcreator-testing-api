package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class CCInputTextImpl implements CCInputText {
    private final WebElement inputElement;
    private WebElement webElement;

    public CCInputTextImpl(WebElement webElement) {
        this.webElement = webElement;
        this.inputElement = webElement.findElement(By.className("gwt-TextBox"));
    }

    @Override
    public String text() {
        return inputElement.getAttribute("value");
    }

    @Override
    public void setText(String text) {
        inputElement.sendKeys(text);
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("gwt-Label")).getText();
    }

    public static boolean isComponent(WebElement webElement, WebDriver webDriver) {
        //TODO: transfer this implementation to a method
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            webElement.findElement(By.className("gwt-TextBox"));
            return true;
        } catch(NoSuchElementException exception) {
            return false;
        } finally {
            webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }
}
