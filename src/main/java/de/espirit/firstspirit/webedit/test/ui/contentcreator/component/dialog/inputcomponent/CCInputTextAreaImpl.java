package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CCInputTextAreaImpl implements CCInputTextArea {
    private final WebElement inputElement;
    private WebElement webElement;

    public CCInputTextAreaImpl(@NotNull final WebElement webElement) {
        this.webElement = webElement;
        this.inputElement = webElement.findElement(By.className("gwt-TextArea"));
    }

    @Override
    public String text() {
        return inputElement.getAttribute("value");
    }

    @Override
    public void setText(String text) {
        inputElement.clear();
        inputElement.sendKeys(text);
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("gwt-Label")).getText();
    }

    public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("gwt-TextArea")) &&
                !ComponentUtils.hasElement(webElement, webDriver, By.tagName("iframe"));
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }

}
