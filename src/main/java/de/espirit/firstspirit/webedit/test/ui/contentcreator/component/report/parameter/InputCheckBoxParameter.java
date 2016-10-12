package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputCheckboxItemImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InputCheckBoxParameter extends CCInputCheckboxItemImpl implements CCInputComponent {
    public InputCheckBoxParameter(@NotNull WebElement webElement) {
        super(webElement, webDriver);
        this.webElement = webElement.findElement(By.className("fs-checkbox"));
    }
}
