package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComboBoxImpl;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputComboBoxParameter extends CCInputComboBoxImpl {
    public InputComboBoxParameter(@NotNull WebElement webElement, @NotNull WebDriver webDriver) {
        super(webElement, webDriver);
    }

    @Override
    public String label() {
        return webElement.findElement(By.className("fs-listbox-text")).getAttribute("value");
    }
}
