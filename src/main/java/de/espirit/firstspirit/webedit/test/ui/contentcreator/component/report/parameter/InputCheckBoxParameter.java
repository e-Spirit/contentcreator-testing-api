package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputCheckboxItemImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputCheckBoxParameter extends CCInputCheckboxItemImpl implements CCInputComponent {
    public InputCheckBoxParameter(@NotNull WebElement webElement, @NotNull final WebDriver driver) {
        super(webElement, driver);
        this.webElement = Utils.findItemInElement(driver, webElement, By.className("fs-checkbox"));
    }
}
