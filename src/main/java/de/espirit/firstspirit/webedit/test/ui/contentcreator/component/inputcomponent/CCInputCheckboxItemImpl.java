package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CCInputCheckboxItemImpl implements CCInputCheckbox.CCInputCheckboxItem {
    protected WebElement webElement;

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
