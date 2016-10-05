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

    public CCInputCheckboxImpl(WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("gwt-Label")).getText();
    }

    @Override
    public List<CCInputCheckboxItem> values() {
        final List<WebElement> elements = webElement.findElements(By.cssSelector("fs-checkbox"));
        final List<CCInputCheckboxItem> resultList= new ArrayList<>();
        for (WebElement element : elements) {
            resultList.add(new CCInputCheckboxItemImpl(element));
        }
        return resultList;
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
        public String value() {
            return webElement.findElement(By.cssSelector("fs-checkbox-label")).getText();
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
