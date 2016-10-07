package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

public abstract class AbstractReport implements Report {
    private WebDriver webDriver;
    protected WebElement reportButton;
    private boolean isVisible = false;

    public AbstractReport(@NotNull final WebDriver webDriver, @NotNull final WebElement reportButton) {
        this.webDriver = webDriver;
        this.reportButton = reportButton;
    }

    @Override
    public int getResultCount() {
        if(isVisible) {
            final WebElement element = find(webDriver, By.className("fs-sidebar-report-status-count"));
            final String text = element.getText(); // text = "Ergebnisse: 47"
            if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
                return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
            }
        }
        return -1;
    }

    @Override
    public WebElement getEntry(final int pos) {
        if(isVisible) {
            return find(webDriver, By.cssSelector("div.report-entry-container > div > div > div:nth-child(1) > div:nth-child(" + (pos + 1) + ") > div > a"));
        }

        return null;
    }

    @Override
    public void toggle() {
        this.isVisible = !isVisible;
        reportButton.click();
    }

    @Override
    public void reload() {
        if(isVisible) {
            find(webDriver, By.className("fs-sidebar-report-status-refresh")).click();
        }
    }

    @Override
    public List<CCInputComponent> parameters() {
        if(!isVisible) {
            toggle();
        }

        WebElement reportContent = new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("fs-sidebar-content")));
        WebElement parametersDiv = reportContent.findElement(By.className("fs-sidebar-report-parameter"));
        List<WebElement> elements = parametersDiv.findElements(By.cssSelector("div > table > tbody > tr"));
        List<CCInputComponent> result = new ArrayList<>();

        for (WebElement element : elements) {
            CCInputComponent ccInputComponent = ComponentUtils.matchParameter(element, webDriver);

            if (ccInputComponent != null) result.add(ccInputComponent);
        }

        return result;
    }

    @NotNull
    @Override
    public WebElement html() {
        if(ComponentUtils.hasElement(webDriver, By.className("fs-sidebar-content")))
            return webDriver.findElement(By.className("fs-sidebar-content"));

        return null;
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
}