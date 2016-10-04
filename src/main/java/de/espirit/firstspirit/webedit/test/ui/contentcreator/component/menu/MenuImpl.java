package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MenuImpl implements Menu {

    private WebElement menuElement;

    public MenuImpl(WebElement menuElement) {
        this.menuElement = menuElement;
    }

    @Override
    public MenuItem menuItem(String displayName) {
        List<WebElement> items = menuElement.findElements(By.tagName("li"));

        WebElement item = items.stream().filter(i -> i.getText().equals(displayName)).findFirst().orElse(null);

        if (item != null) return new MenuItemImpl(item);
        else return null;
    }

    @NotNull
    @Override
    public WebElement html() {
        return menuElement;
    }
}