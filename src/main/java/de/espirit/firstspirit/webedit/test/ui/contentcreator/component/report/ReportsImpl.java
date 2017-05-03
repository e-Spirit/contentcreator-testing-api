package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class ReportsImpl implements Reports {
    private final WebDriver webDriver;

    public ReportsImpl(@NotNull final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @NotNull
    @Override
    public SearchReport search() throws CCAPIException {
        final WebElement searchReportButton = Utils.findElementByFsElementName(this.webDriver, By.cssSelector("div.fs-sidebar-buttons > div"), "Search");
        Objects.requireNonNull(searchReportButton);
        return new SearchReportImpl(this.webDriver, searchReportButton);
    }

    @NotNull
    @Override
    public Report bookmarks() throws CCAPIException {
        return customByFsElementName("Bookmarks");
    }

    @NotNull
    @Override
    public Report tasks() throws CCAPIException {
        return customByFsElementName("Tasks");
    }

    @NotNull
    @Override
    public Report history() throws CCAPIException {
        return customByFsElementName("History");
    }

    @NotNull
    @Override
    public Report relations() throws CCAPIException {
        return customByFsElementName("RelatedObjects");
    }

    @NotNull
    @Override
    public Report notifications() throws CCAPIException {
        return customByFsElementName("Notifications");
    }

    @NotNull
    public Report customByFsElementName(final String fsElementName) throws CCAPIException {
        final WebElement button = Utils.findElementByFsElementName(this.webDriver, By.cssSelector("div.fs-sidebar-buttons > div"), fsElementName);

        if(button == null)
            throw new CCAPIException("Can't find report with the fs element name: '" + fsElementName + "'", this.webDriver);

        return new CustomReport(this.webDriver, button);
    }

    @NotNull
    @Override
    public WebElement html() throws CCAPIException {
        return Utils.findElement(this.webDriver, By.cssSelector(".fs-sidebar"));
    }
}