package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

public class SearchReportImpl extends AbstractReport implements SearchReport {
    private WebDriver webDriver;

    public SearchReportImpl(@NotNull WebDriver webDriver, @NotNull WebElement button) {
        super(webDriver, button);
        this.webDriver = webDriver;
    }

    @Override
    public void setParamMyElements(final boolean onlyMyElements) throws CCAPIException {
        final WebElement webElement = find(webDriver, By.className("fs-checkbox-label"));
        if (webElement.getAttribute("class").contains("fs-checkbox-checked") != onlyMyElements) {
            webElement.click();
        }
    }

    @Nullable
    @Override
    public String reportMessage() throws CCAPIException {
        final WebElement reportMsg = find(webDriver, By.className("report-message"));
        return reportMsg.isDisplayed() ? reportMsg.getText() : null;
    }

    @NotNull
    @Override
    public WebElement html() {
        if(ComponentUtils.hasElement(webDriver, By.className("fs-sidebar-content")))
            return webDriver.findElement(By.className("fs-sidebar-content"));

        return null;
    }

    @NotNull
    @Override
    public WebElement button() {
        return reportButton;
    }
}