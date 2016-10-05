package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

public abstract class AbstractReport implements Report {

    private WebDriver webDriver;

    public AbstractReport(@NotNull final WebDriver webDriver) {

        this.webDriver = webDriver;
    }

    @Override
    public int getResultCount() {
        final WebElement element = find(webDriver, By.className("fs-sidebar-report-status-count"));
        final String text = element.getText(); // text = "Ergebnisse: 47"
        if (!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1))) {
            return Integer.parseInt(text.substring(text.lastIndexOf(' ') + 1));
        }
        return -1;
    }

    @Override
    public WebElement getEntry(final int pos) {
        return find(webDriver, By.cssSelector("div.report-entry-container > div > div > div:nth-child(1) > div:nth-child(" + (pos + 1) + ") > div > a"));
    }

    @Override
    public void toggle() {
        button().click();
    }

    @Override
    public void reload() {
        find(webDriver, By.className("fs-sidebar-report-status-refresh")).click();
    }

    /**
     * Returns the report's button.
     *
     * @return report's button.
     */
    @NotNull
    protected abstract WebElement button();

}