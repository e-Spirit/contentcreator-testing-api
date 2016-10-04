package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

        try {
            new WebDriverWait(webDriver, 0).until(ExpectedConditions.presenceOfElementLocated(By.className("gwt-TextBox")));
        }
        catch(NoSuchElementException exception) {
            return false;
        }

        return true;
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }
}
