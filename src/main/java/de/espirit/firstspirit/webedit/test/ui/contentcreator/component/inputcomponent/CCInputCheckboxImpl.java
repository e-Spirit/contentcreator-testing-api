package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CCInputCheckboxImpl implements CCInputCheckbox {
    private WebElement webElement;
    private final List<WebElement> items;

    public CCInputCheckboxImpl(@NotNull final WebElement webElement) {
        this.webElement = webElement;
        this.items = webElement.findElements(By.className("fs-checkbox"));
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("gwt-Label")).getText();
    }

    @Override
    public List<CCInputCheckboxItem> items() {
        final List<CCInputCheckboxItem> resultList= new ArrayList<>();
        for (WebElement element : items) {
            resultList.add(new CCInputCheckboxItemImpl(element));
        }
        return resultList;
    }

    @Override
    public CCInputCheckboxItem itemByName(@NotNull String displayName) {
        for (WebElement element : items) {
            if(webElement.findElement(By.className("fs-checkbox-label")).getText().equals(displayName))
                return new CCInputCheckboxItemImpl(element);
        }
        return null;
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }

    public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-checkbox"));
    }
}
