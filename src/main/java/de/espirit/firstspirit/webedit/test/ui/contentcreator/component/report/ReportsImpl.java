package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ReportsImpl implements Reports {

    private final WebDriver webDriver;

    public ReportsImpl(@NotNull final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @NotNull
    @Override
    public SearchReport search() throws CCAPIException {
        WebElement searchReportButton = Utils.findElement(webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(1)"));
        return new SearchReportImpl(webDriver, searchReportButton);
    }

    @NotNull
    @Override
    public Report bookmarks() throws CCAPIException {
        return custom(-5);
    }

    @NotNull
    @Override
    public Report workflows() throws CCAPIException {
        return custom(-4);
    }

    @NotNull
    @Override
    public Report history() throws CCAPIException {
        return custom(-3);
    }

    @NotNull
    @Override
    public Report relations() throws CCAPIException {
        return custom(-2);
    }

    @NotNull
    @Override
    public Report messages() throws CCAPIException {
        return custom(-1);
    }

    @NotNull
    @Override
    public Report custom(final int no) throws CCAPIException {
        WebElement button = Utils.findElement(webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div:nth-child(" + (7 + no) + ')'));
        return new CustomReport(webDriver, button);
    }

    @NotNull
    @Override
    public Report customByName(String displayName) throws CCAPIException {
        List<WebElement> reportElements = Utils.findElements(webDriver, By.cssSelector(".fs-sidebar-buttons:nth-child(2) > div"));

        for (WebElement reportElement : reportElements) {
            WebElement displayTextElement = Utils.findElement(webDriver, By.cssSelector("div.text"));
            if (displayTextElement.getAttribute("textContent").equals(displayName))
                return new CustomReport(webDriver, reportElement);
        }

        return null;
    }

    @NotNull
    @Override
    public WebElement html() throws CCAPIException {
        return Utils.findElement(webDriver, By.cssSelector(".fs-sidebar"));
    }
}