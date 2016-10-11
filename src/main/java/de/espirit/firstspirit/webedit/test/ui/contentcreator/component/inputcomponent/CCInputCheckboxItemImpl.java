package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class CCInputCheckboxItemImpl implements CCInputCheckbox.CCInputCheckboxItem {
    protected WebElement webElement;
    private WebDriver webDriver;

    public CCInputCheckboxItemImpl(WebElement webElement, WebDriver webDriver) {
        this.webElement = webElement;
        this.webDriver = webDriver;
    }

    @Override
    public String label() throws CCAPIException {
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
