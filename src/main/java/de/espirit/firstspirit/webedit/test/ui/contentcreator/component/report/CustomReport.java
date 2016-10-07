package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomReport extends AbstractReport {
    public CustomReport(@NotNull WebDriver webDriver, @NotNull WebElement reportButton) {
        super(webDriver, reportButton);
    }
}
