package de.espirit.firstspirit.webedit.test.ui.contentcreator;

import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.CCDialogs;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.CCDialogsImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBar;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu.MenuBarImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.preview.PreviewImpl;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.Reports;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.ReportsImpl;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.find;

/**
 * Implementation of the {@link CC WebEdit-UI-adapter} and all it's depending interfaces.
 */
public class CCImpl implements CC {
    private static final Logger LOGGER = Logger.getLogger(CCImpl.class);

    private final org.openqa.selenium.WebDriver _driver;
    private final Project _project;

    public CCImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String url, final String ssoTicket) {
        this(project, driver, url + "&login.ticket=" + ssoTicket);
    }

    CCImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String webEditUrl) {
        _project = project;
        _driver = driver;
        _driver.get(webEditUrl);
    }

    @NotNull
    @Override
    public WebElement html() {
        return find(_driver, By.tagName("html"));
    }

    @Override
    public Preview preview() {
        final WebElement body = find(_driver, By.tagName("body"));
        final WebElement iframe = find(_driver, By.id("previewContent"));
        return new PreviewImpl(_driver, body, iframe);
    }

    @Override
    public MenuBar menu() {
        return new MenuBarImpl(_driver);
    }

    @Override
    public Reports reports() {
        return new ReportsImpl(_driver);
    }

    @Override
    public void reload() {
        driver().navigate().refresh();
    }

    @Override
    public org.openqa.selenium.WebDriver driver() {
        return _driver;
    }

    @Override
    public Project project() {
        return _project;
    }

    @Override
    public CCDialogs dialogs() {
        return new CCDialogsImpl(_driver);
    }

    @Override
    public void navigateTo(@NotNull PageRef pageRef) {
        ((JavascriptExecutor)_driver).executeScript("top.WE_API.Common.jumpTo({\"id\":"+pageRef.getId()+", \"store\":\"SITESTORE\"});");
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
