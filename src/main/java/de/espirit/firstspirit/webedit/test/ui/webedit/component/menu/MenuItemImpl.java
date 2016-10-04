package de.espirit.firstspirit.webedit.test.ui.webedit.component.menu;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;

public class MenuItemImpl implements MenuItem {
    private WebElement item;

    public MenuItemImpl(WebElement item) {
        this.item = item;
    }

    @Override
    public void click() {
        item.click();
    }

    @NotNull
    @Override
    public WebElement html() {
        return item;
    }
}
