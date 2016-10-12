package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.parameter;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputTextImpl;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputTextParameter extends CCInputTextImpl {
    public InputTextParameter(@NotNull WebElement webElement, @NotNull final WebDriver webDriver) {
        super(webElement, webDriver);
    }

    @Override
    public String label() {
        return inputElement.getAttribute("placeholder");
    }
}
