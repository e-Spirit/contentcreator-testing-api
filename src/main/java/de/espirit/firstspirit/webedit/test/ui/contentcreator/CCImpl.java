package de.espirit.firstspirit.webedit.test.ui.contentcreator;

import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.CCDialogs;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.CCDialogsImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBar;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBarImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.PreviewImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.Reports;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.ReportsImpl;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.FS;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.waitForCC;

/**
 * Implementation of the {@link CC WebEdit-UI-adapter} and all it's depending interfaces.
 */
public class CCImpl implements CC {
    private static final Logger LOGGER = Logger.getLogger(CCImpl.class);

    private final org.openqa.selenium.WebDriver webDriver;
    private final Project project;
    private final FS fs;

    public CCImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String url, final String ssoTicket, final FS fs) {
        this(project, driver, url + "&login.ticket=" + ssoTicket, fs);
    }

    CCImpl(final Project project, final WebDriver driver, final String webEditUrl, FS fs) {
        this.project = project;
        this.webDriver = driver;
        this.fs = fs;
        this.webDriver.get(webEditUrl);
    }

    @NotNull
    @Override
    public WebElement html() throws CCAPIException {
        return Utils.findElement(webDriver, By.tagName("html"));
    }

    @Override
    public Preview preview() throws CCAPIException {
        final WebElement body = Utils.findElement(webDriver, By.tagName("body"));
        final WebElement iframe = Utils.findElement(webDriver, By.id("previewContent"));
        return new PreviewImpl(webDriver, body, iframe);
    }

    @Override
    public MenuBar menu() {
        return new MenuBarImpl(webDriver);
    }

    @Override
    public Reports reports() {
        return new ReportsImpl(webDriver);
    }

    @Override
    public void reload() {
        driver().navigate().refresh();
    }

    @Override
    public org.openqa.selenium.WebDriver driver() {
        return webDriver;
    }

    @Override
    public Project project() {
        return project;
    }

    @Override
    public CCDialogs dialogs() {
        return new CCDialogsImpl(webDriver);
    }

    @Override
    public long previewElementId() {
        waitForCC(webDriver);
        Long id = (Long) ((JavascriptExecutor) webDriver).executeScript("return typeof top.WE_API.Common.getPreviewElement().getId()");

        return id;
    }

    @Override
    public void logout() {
        try {
            ((RemoteWebDriver) driver()).executeScript("location.href='logout.jsp';");
        } catch (final Exception e) {
            LOGGER.warn("exception during logout", e);
        }
    }
}
