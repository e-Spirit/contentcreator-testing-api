package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MenuImpl implements Menu {
    private WebDriver webDriver;
    private WebElement button;

    public MenuImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement button) {
        this.webDriver = webDriver;
        this.button = button;
    }

    @Override
    public MenuItem menuItem(@NotNull final String displayName) {
        WebElement menuElement = open();
        List<WebElement> items = menuElement.findElements(By.tagName("li"));

        WebElement item = items.stream().filter(i -> i.getText().equals(displayName)).findFirst().orElse(null);

        if (item != null)
            return new MenuItemImpl(this, item.getText());
        else
            return null;
    }

    @Override
    public WebElement open() {
        new Actions(webDriver).moveToElement(button).perform();
        return new WebDriverWait(webDriver, 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.gwt-PopupPanel.fs-toolbar-flyout")));
    }

    @NotNull
    @Override
    public WebElement html() {
        return open();
    }
}