package de.espirit.firstspirit.webedit.test.ui.webedit.component.report;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

public class SearchReportImpl extends AbstractReport implements SearchReport {

    private WebDriver webDriver;

    public SearchReportImpl(@NotNull WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    @Override
    public void setParamMyElements(final boolean onlyMyElements) {
        final WebElement webElement = find(webDriver, By.className("fs-checkbox-label"));
        if (webElement.getAttribute("class").contains("fs-checkbox-checked") != onlyMyElements) {
            webElement.click();
        }
    }

    @Nullable
    @Override
    public String reportMessage() {
        final WebElement reportMsg = find(webDriver, By.className("report-message"));
        return reportMsg.isDisplayed() ? reportMsg.getText() : null;
    }

    @NotNull
    @Override
    public WebElement html() {
        return find(webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
    }

    @NotNull
    @Override
    public WebElement button() {
        return find(webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
    }
}