package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class CCInputButtonImpl implements CCInputButton {
    private final WebDriver webDriver;
    private WebElement buttonElement;

    public CCInputButtonImpl(WebDriver webDriver, @NotNull final WebElement webElement) {
        this.webDriver = webDriver;
        if (webElement.getAttribute("class").contains("fs-button"))
            this.buttonElement = webElement;
        else
            this.buttonElement = webElement.findElement(By.className("fs-button"));
    }

    @Override
    public String label() {
        return buttonElement.findElement(By.className("fs-button-text")).getText();
    }

    @Override
    public void click() {
        buttonElement.click();
    }

    @Override
    public boolean enabled() {
        return !buttonElement.getAttribute("class").contains("fs-button-disabled");
    }

    public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-button"));
    }

    @NotNull
    @Override
    public WebElement html() {
        return buttonElement;
    }
}
