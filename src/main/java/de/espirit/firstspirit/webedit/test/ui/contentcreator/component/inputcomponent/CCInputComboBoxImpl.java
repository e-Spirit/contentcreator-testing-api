package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Notes: The combobox items are not actually present within the html.
 * To provide access to the combobox items a dynamic parsing is required.
 * This implementation should be temporary.
 * TODO: use javascript to fire the specific event
 **/
public class CCInputComboBoxImpl implements CCInputComboBox {
    protected WebElement webElement;
    private WebDriver webDriver;

    public CCInputComboBoxImpl(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        this.webElement = webElement;
        this.webDriver = webDriver;
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("gwt-Label")).getText();
    }

    @NotNull
    @Override
    public WebElement html() {
        return webElement;
    }

    @Override
    public List<CCInputComboBoxItem> items() {
        final List<CCInputComboBox.CCInputComboBoxItem> resultList= new ArrayList<>();
        WebElement listBoxElement = webElement.findElement(By.className("fs-listbox-text"));

        listBoxElement.click();
        WebElement listBoxPopup = new WebDriverWait(webDriver, 30).until(ExpectedConditions.presenceOfElementLocated(By.className("fs-listbox-popup")));
        List<WebElement> options = listBoxPopup.findElements(By.tagName("option"));

        for (WebElement option : options) {
            resultList.add(new CCInputComboBoxItemImpl(webDriver, webElement, option.getText(), option.getAttribute("value")));
        }
        listBoxElement.click();
        return resultList;
    }

    @Override
    public CCInputComboBox.CCInputComboBoxItem itemByName(@NotNull String displayName) {
        WebElement listBoxElement = webElement.findElement(By.className("fs-listbox-text"));
        listBoxElement.click();

        WebElement listBoxPopup = new WebDriverWait(webDriver, 30).until(ExpectedConditions.presenceOfElementLocated(By.className("fs-listbox-popup")));
        List<WebElement> options = listBoxPopup.findElements(By.tagName("option"));

        for (WebElement option : options) {
            if(option.getText().equals(displayName))
            {
                CCInputComboBoxItemImpl inputComboBoxItem = new CCInputComboBoxItemImpl(webDriver, listBoxElement, option.getText(), option.getAttribute("value"));
                listBoxElement.click();
                return inputComboBoxItem;
            }
        }
        listBoxElement.click();
        return null;
    }

    @Override
    public CCInputComboBoxItem selectedItem() {
        WebElement listBoxElement = webElement.findElement(By.className("fs-listbox-text"));

        listBoxElement.click();
        WebElement listBoxPopup = new WebDriverWait(webDriver, 30).until(ExpectedConditions.presenceOfElementLocated(By.className("fs-listbox-popup")));
        List<WebElement> options = listBoxPopup.findElements(By.tagName("option"));
        String value = listBoxElement.getAttribute("value");

        for (WebElement option : options) {
            if(option.getAttribute("value").equals(value))
            {
                CCInputComboBoxItemImpl inputComboBoxItem = new CCInputComboBoxItemImpl(webDriver, webElement, option.getText(), option.getAttribute("value"));
                listBoxElement.click();
                return inputComboBoxItem;
            }
        }
        listBoxElement.click();
        return null;
    }

    public static boolean isComponent(@NotNull final WebElement webElement, @NotNull final WebDriver webDriver) {
        return ComponentUtils.hasElement(webElement, webDriver, By.className("fs-listbox-text"));
    }

    private class CCInputComboBoxItemImpl implements CCInputComboBox.CCInputComboBoxItem {
        private WebDriver webDriver;
        private WebElement listBoxElement;
        private String label;
        private String value;

        public CCInputComboBoxItemImpl(@NotNull final WebDriver webDriver, @NotNull final WebElement listBoxElement, @NotNull final String label, @NotNull final String value) {
            this.webDriver = webDriver;
            this.listBoxElement = listBoxElement;
            this.label = label;
            this.value = value;
        }

        @Override
        public void select(){
            listBoxElement.click();
            WebElement listBoxPopup = new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("fs-listbox-popup")));
            List<WebElement> options = listBoxPopup.findElements(By.tagName("option"));

            for (WebElement option : options) {
                if(option.getAttribute("value").equals(value))
                {
                    option.click();
                    return;
                }
            }
        }

        @Override
        public String label() {
            return label;
        }
    }
}
