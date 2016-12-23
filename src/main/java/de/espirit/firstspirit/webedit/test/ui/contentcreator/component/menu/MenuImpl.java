package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class MenuImpl implements Menu {
    private WebDriver webDriver;
    private By selector;

    public MenuImpl(@NotNull final WebDriver webDriver, @NotNull final By selector) {
        this.webDriver = webDriver;
        this.selector = selector;
    }

    @Override
    public MenuItem menuItem(@NotNull final String displayName) throws CCAPIException {
        WebElement menuElement = open();

        try {
            new WebDriverWait(webDriver, Constants.WEBDRIVER_WAIT).until((ExpectedCondition<Boolean>) d -> {
                final List<WebElement> li = Utils.findMultipleItemsInElement(webDriver, menuElement, By.tagName("li"));
                for (WebElement element : li) {
                    if (element.getText().equals(displayName))
                        return true;
                }
                return false;
            });
        } catch (RuntimeException e) {
            throw new CCAPIException(e.getMessage(), webDriver);
        }

        List<WebElement> items = Utils.findMultipleItemsInElement(webDriver, menuElement, By.tagName("li"));
        WebElement item = items.stream().filter(i -> i.getText().equals(displayName)).findFirst().orElse(null);

        if (item != null)
            return new MenuItemImpl(this, item.getText(), webDriver);
        else
            throw new CCAPIException("Can't find menu item with the displayname: '"+displayName+"'", webDriver);
    }


    @Override
    public List<MenuItem> menuItems() throws CCAPIException {
        final WebElement menuElement = open();
        final List<WebElement> menuWebElements = Utils.findMultipleItemsInElement(webDriver, menuElement, By.tagName("li"));
        final List<MenuItem> menuItems = new ArrayList<>();

        for (WebElement menuItem : menuWebElements) {
            menuItems.add(new MenuItemImpl(this, menuItem.getText(), webDriver));
        }

        return menuItems;
    }

    /**
     * TODO: Move this in order to make use of this elsewhere. See: PSCCT-15
     */
    public static final String JS_MOUSEOVER = "var evObj = document.createEvent('MouseEvents');" +
            "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
            "arguments[0].dispatchEvent(evObj);";

    @Override
    public WebElement open() throws CCAPIException {
        WebElement button = Utils.find(webDriver, ExpectedConditions.elementToBeClickable(selector));
        ((JavascriptExecutor) webDriver).executeScript(JS_MOUSEOVER, button);
        return Utils.find(webDriver, ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.fs-toolbar-flyout")));
    }


    @Override
    public WebElement html() throws CCAPIException {
        return open();
    }
}