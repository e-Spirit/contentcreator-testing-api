package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent.util.ComponentUtils;
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

    public static boolean isComponent(WebElement webElement, WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-checkbox"));
    }

    private class CCInputCheckboxItemImpl implements CCInputCheckbox.CCInputCheckboxItem{

        private WebElement webElement;

        public CCInputCheckboxItemImpl(WebElement webElement) {
            this.webElement = webElement;
        }

        @Override
        public String label() {
            return webElement.findElement(By.className("fs-checkbox-label")).getText();
        }

        @Override
        public boolean checked() {
            return webElement.getAttribute("class").contains("fs-checkbox-checked");
        }

        @Override
        public void toggle() {
            webElement.click();
        }

        @NotNull
        @Override
        public WebElement html() {
            return webElement;
        }
    }
}
