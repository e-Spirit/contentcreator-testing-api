package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CCInputCheckboxImpl implements CCInputCheckbox {
    private WebElement webElement;
    private final List<WebElement> items;
    private WebDriver webDriver;

    public CCInputCheckboxImpl(@NotNull final WebElement webElement, WebDriver webDriver) throws CCAPIException {
        this.webElement = webElement;
        this.items = Utils.findMultipleItemsInElement(webDriver, webElement, By.className("fs-checkbox"));
        this.webDriver = webDriver;
    }

    @Override
    public String label() throws CCAPIException {
        return Utils.findItemInElement(webDriver, webElement, By.className("gwt-Label")).getText();

    }

    @Override
    public List<CCInputCheckboxItem> items() {
        List<CCInputCheckboxItem> resultList;
        resultList = new ArrayList<>();
        for (WebElement element : items) {
            resultList.add(new CCInputCheckboxItemImpl(element, webDriver));
        }
        return resultList;
    }

    @Override
    public CCInputCheckboxItem itemByName(@NotNull String displayName) throws CCAPIException {
        for (WebElement element : items) {
            if (Utils.findItemInElement(webDriver, webElement, By.className("fs-checkbox-label")).getText().equals(displayName))
                return new CCInputCheckboxItemImpl(element, webDriver);
        }

        throw new CCAPIException("Can't find item with the displayname: '"+displayName+"'", webDriver);
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
