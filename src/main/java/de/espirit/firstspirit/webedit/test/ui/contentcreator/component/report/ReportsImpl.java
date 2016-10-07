package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

public class ReportsImpl implements Reports {

    private WebDriver webDriver;

    public ReportsImpl(@NotNull final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @NotNull
    @Override
    public SearchReport search() {
        WebElement searchReportButton = webDriver.findElement(By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
        return new SearchReportImpl(webDriver, searchReportButton);
    }

    @NotNull
    @Override
    public Report bookmarks() {
        return custom(-5);
    }

    @NotNull
    @Override
    public Report workflows() {
        return custom(-4);
    }

    @NotNull
    @Override
    public Report history() {
        return custom(-3);
    }

    @NotNull
    @Override
    public Report relations() {
        return custom(-2);
    }

    @NotNull
    @Override
    public Report messages() {
        return custom(-1);
    }

    @NotNull
    @Override
    public Report custom(final int no) {
        WebElement button = find(webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(" + (7 + no) + ')'));
        return new CustomReport(webDriver, button);
    }

    @NotNull
    @Override
    public Report customByName(String displayName) {
        List<WebElement> reportElements = webDriver.findElements(By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div"));

        for (WebElement reportElement : reportElements) {
            WebElement displayTextElement = reportElement.findElement(By.cssSelector("div.text"));
            if(displayTextElement.getAttribute("textContent").equals(displayName))
                return new CustomReport(webDriver, reportElement);
        }

        return null;
    }

    @NotNull
    @Override
    public WebElement html() {
        return find(webDriver, By.cssSelector(".fs-sidebar"));
    }
}