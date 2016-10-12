package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CCInputTextAreaImpl implements CCInputTextArea {
    private final WebElement inputElement;
    private WebDriver webDriver;
    private WebElement webElement;

    public CCInputTextAreaImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement webElement) throws CCAPIException {
        this.webDriver = webDriver;
        this.webElement = webElement;
        this.inputElement = Utils.findItemInElement(webDriver, webElement, By.className("gwt-TextArea"));
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
    public String label() throws CCAPIException {
        return Utils.findItemInElement(webDriver, webElement, By.className("gwt-Label")).getText();
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
