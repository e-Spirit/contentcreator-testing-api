package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CCInputDomImpl implements CCInputDom {
    private WebElement webElement;
    private WebDriver webDriver;

    public CCInputDomImpl(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        this.webElement = webElement;
        this.webDriver = webDriver;
    }

    @Override
    public String text() throws CCAPIException {
        return getText();
    }

    @Override
    public String getText() throws CCAPIException {
        WebElement iframe = Utils.findItemInElement(webDriver, webElement, By.tagName("iframe"));
        webDriver.switchTo().frame(iframe);
        WebElement tinymce = Utils.find(webDriver, ExpectedConditions.presenceOfElementLocated(By.id("tinymce")));
        List<WebElement> custom = Utils.findMultipleItemsInElement(webDriver, tinymce, By.className("custom"));
        String result = custom.get(0).getText();
        webDriver.switchTo().defaultContent();
        return result;
    }

    @Override
    public void setText(String text) throws CCAPIException {
        WebElement iframe = Utils.findItemInElement(webDriver, webElement, By.tagName("iframe"));
        webDriver.switchTo().frame(iframe);

        WebElement tinymce = Utils.find(webDriver, ExpectedConditions.presenceOfElementLocated(By.id("tinymce")));
        List<WebElement> custom = Utils.findMultipleItemsInElement(webDriver, tinymce, By.className("custom"));
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
