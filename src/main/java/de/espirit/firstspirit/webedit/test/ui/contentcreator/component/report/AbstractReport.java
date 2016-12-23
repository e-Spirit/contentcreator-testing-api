package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import de.espirit.firstspirit.webedit.test.ui.util.CustomConditions;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReport implements Report {
    private final WebDriver webDriver;
    protected WebElement reportButton;

    public AbstractReport(@NotNull final WebDriver webDriver, @NotNull final WebElement reportButton) {
        this.webDriver = webDriver;
        this.reportButton = reportButton;
    }

    @Override
    public int getResultCount() throws CCAPIException {
        if (isVisible()) {
            final WebElement element = Utils.find(webDriver, ExpectedConditions.presenceOfElementLocated(By.className("fs-sidebar-report-status-count")));
            new WebDriverWait(webDriver, Constants.WEBDRIVER_WAIT).until(CustomConditions.resultChanged(element));
            final String text = element.getText();
            if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
                return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
            }
        }
        return -1;
    }

    @Override
    public WebElement getEntry(final int pos) throws CCAPIException {
        if (isVisible()) {
            return Utils.findElement(webDriver, By.cssSelector("div.report-entry-container > div > div > div:nth-child(1) > div:nth-child(" + (pos + 1) + ") > div"));
        }

        return null;
    }

    @Override
    public void toggle() {
        WebElement button = Utils.find(webDriver, ExpectedConditions.elementToBeClickable(reportButton));
        button.click();
    }

    @Override
    public void reload() throws CCAPIException {
        if (isVisible()) {
            Utils.findElement(webDriver, By.className("fs-sidebar-report-status-refresh")).click();
        }
    }

    @Override
    public List<CCInputComponent> parameters() throws CCAPIException {
        if (!isVisible()) {
            toggle();
        }

        WebElement reportContent = Utils.find(webDriver, ExpectedConditions.visibilityOfElementLocated(By.className("fs-sidebar-content")));
        WebElement parametersDiv = Utils.findItemInElement(webDriver, reportContent, By.className("fs-sidebar-report-parameter"));
        List<WebElement> elements = Utils.findMultipleItemsInElement(webDriver, parametersDiv, By.cssSelector("div > table > tbody > tr"));
        List<CCInputComponent> result = new ArrayList<>();

        for (WebElement element : elements) {
            CCInputComponent ccInputComponent = ComponentUtils.matchParameter(element, webDriver);

            if (ccInputComponent != null)
                result.add(ccInputComponent);
        }

        return result;
    }

    @NotNull
    @Override
    public WebElement html() throws CCAPIException {
        if (!isVisible()) {
            toggle();
        }

        return Utils.findElement(webDriver, By.className("fs-sidebar-content"));
    }

    /**
     * Returns the report's reportButton.
     *
     * @return report's reportButton.
     */
    @NotNull
    public WebElement button() {
        return reportButton;
    }

    public boolean isVisible() {
        return reportButton.getAttribute("class").contains("pressed");
    }
}