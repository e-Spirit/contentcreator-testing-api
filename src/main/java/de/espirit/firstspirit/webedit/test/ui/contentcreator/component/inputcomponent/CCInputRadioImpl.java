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

public class CCInputRadioImpl implements CCInputRadio {
    private final WebDriver webDriver;
    private WebElement webElement;
    private List<WebElement> items;

    public CCInputRadioImpl(WebDriver webDriver, @NotNull final WebElement webElement) throws CCAPIException {
        this.webDriver = webDriver;
        this.webElement = webElement;
        this.items = Utils.findMultipleItemsInElement(webDriver, webElement, By.className("fs-radiobutton"));

    }

    @Override
    public String label() throws CCAPIException {
        return Utils.findItemInElement(webDriver, webElement, By.className("gwt-Label")).getText();
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }

    @Override
    public List<CCInputRadioItem> items() {
        final List<CCInputRadioItem> resultList = new ArrayList<>();
        for (WebElement element : items) {
            resultList.add(new CCInputRadioItemImpl(element));
        }
        return resultList;
    }

    @Override
    public CCInputRadioItem itemByName(@NotNull String displayName) throws CCAPIException {
            for (WebElement element : items) {
                if (Utils.findItemInElement(webDriver, element, By.className("fs-radiobutton-label")).getText().equals(displayName))
                    return new CCInputRadioItemImpl(element);
            }
        return null;
    }

    @Override
    public CCInputRadioItem selectedItem() {
        for (WebElement item : items) {
            if (item.getAttribute("class").contains("fs-radiobutton-selected")) {
                return new CCInputRadioItemImpl(item);
            }
        }

        return null;
    }

    public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-radiobutton"));
    }

    private class CCInputRadioItemImpl implements CCInputRadioItem {
        private WebElement element;

        public CCInputRadioItemImpl(@NotNull final WebElement element) {
            this.element = element;
        }

        @NotNull
        @Override
        public WebElement html() {
            return element;
        }

        @Override
        public void select() {
            element.click();
        }

        @Override
        public String label() throws CCAPIException {
            return Utils.findItemInElement(webDriver, element, By.className("fs-radiobutton-label")).getText();
        }

        @Override
        public boolean checked() {
            return element.getAttribute("class").contains("fs-radiobutton-selected");
        }
    }
}
