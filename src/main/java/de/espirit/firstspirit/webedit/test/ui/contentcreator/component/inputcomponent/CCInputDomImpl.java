package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;

import java.util.List;

public class CCInputDomImpl implements CCInputDom {
    private WebElement webElement;
    private WebDriver webDriver;

    public CCInputDomImpl(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        this.webElement = webElement;
        this.webDriver = webDriver;
    }

    @Override
    public String text() {
        String result;
        WebElement iframe = webElement.findElement(By.tagName("iframe"));
        webDriver.switchTo().frame(iframe);
        WebElement tinymce = webDriver.findElement(By.id("tinymce"));
        List<WebElement> custom = tinymce.findElements(By.className("custom"));
        result = custom.get(0).getText();
        webDriver.switchTo().defaultContent();
        return result;
    }

    @Override
    public void setText(String text) {
        WebElement iframe = webElement.findElement(By.tagName("iframe"));
        webDriver.switchTo().frame(iframe);

        WebElement tinymce = webDriver.findElement(By.id("tinymce"));
        List<WebElement> custom = tinymce.findElements(By.className("custom"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].innerHTML='" + text + "'", custom.get(0));

        webDriver.switchTo().defaultContent();
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("gwt-Label")).getText();
    }

    public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("gwt-TextArea")) &&
                ComponentUtils.hasElement(webElement, webDriver, By.tagName("iframe"));
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }
}
