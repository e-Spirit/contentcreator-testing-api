package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MenuImpl implements Menu {
    private WebDriver webDriver;
    private By selector;

    public MenuImpl(@NotNull final WebDriver webDriver, By selector) {
        this.webDriver = webDriver;
        this.selector = selector;
    }

    @Override
    public MenuItem menuItem(@NotNull final String displayName) throws CCAPIException {
        WebElement menuElement = open();
        List<WebElement> items = menuElement.findElements(By.tagName("li"));

        WebElement item = items.stream().filter(i -> i.getText().equals(displayName)).findFirst().orElse(null);

        if (item != null)
            return new MenuItemImpl(this, item.getText());
        else
            return null;
    }
    public static final String JS_MOUSEOVER = "var evObj = document.createEvent('MouseEvents');" +
            "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
            "arguments[0].dispatchEvent(evObj);";
    @Override
    public WebElement open() throws CCAPIException {
        try {
            WebElement button = new WebDriverWait(webDriver, 30).until(ExpectedConditions.elementToBeClickable(selector));
            ((JavascriptExecutor)webDriver).executeScript(JS_MOUSEOVER,button);
            //new Actions(webDriver).moveToElement(button).perform();
            return new WebDriverWait(webDriver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.gwt-PopupPanel.fs-toolbar-flyout")));
        } catch(WebDriverException exception) {
            throw new CCAPIException(exception.getMessage(), webDriver);
        }
    }

    @NotNull
    @Override
    public WebElement html() throws CCAPIException {
        return open();
    }
}