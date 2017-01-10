package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MenuItemImpl implements MenuItem {
    private Menu menu;
    private String label;
    private final WebDriver webDriver;

    public MenuItemImpl(@NotNull final Menu menu, @NotNull final String label, WebDriver webDriver) {
        this.menu = menu;
        this.label = label;
        this.webDriver = webDriver;
    }

    @Override
    public void click() throws CCAPIException {
        WebElement item = html();
        if (item != null) {
            new WebDriverWait(webDriver, Constants.WEBDRIVER_WAIT).until(ExpectedConditions.elementToBeClickable(item));
            item.click();
        }
    }

    @Override
    public WebElement html() throws CCAPIException {
        WebElement menuElement = menu.html();
        List<WebElement> items = Utils.findMultipleItemsInElement(webDriver, menuElement, By.tagName("li"));

        WebElement item = items.stream()
                .filter(i -> i.getText().equals(label))
                .findFirst()
                .orElse(null);

        return item;
    }
}
