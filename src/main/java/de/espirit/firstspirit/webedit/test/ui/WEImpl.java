package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.common.base.Logging;
import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.menu.MenuBar;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.menu.MenuBarImpl;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.preview.PreviewImpl;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.report.Reports;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.report.ReportsImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.idle;

/**
 * Implementation of the {@link WE WebEdit-UI-adapter} and all it's depending interfaces.
 */
public class WEImpl implements WE {

    private final org.openqa.selenium.WebDriver _driver;
    private final Project _project;

    WEImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String url, final String ssoTicket) {
        this(project, driver, url + "&login.ticket=" + ssoTicket);
    }

    WEImpl(final Project project, final org.openqa.selenium.WebDriver driver, final String webEditUrl) {
        _project = project;
        _driver = driver;
        _driver.get(webEditUrl);
    }

    @NotNull
    @Override
    public WebElement html() {
        return find(By.tagName("html"));
    }

    @Override
    public Preview preview() {
        final WebElement body = find(By.tagName("body"));
        final WebElement iframe = find(By.id("previewContent"));
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
    public void logout() {
        try {
            ((RemoteWebDriver) driver()).executeScript("location.href='logout.jsp';");
        } catch (final Exception e) {
            Logging.logWarning("exception during logout", e, WEImpl.class);
        }
    }

    @NotNull
    private WebElement find(@NotNull final By by) {
        return find(null, by);
    }

    @NotNull
    private WebElement find(@Nullable final SearchContext webElement, @NotNull final By by) {
        idle();
        return webElement != null ? webElement.findElement(by) : _driver.findElement(by);
    }

}
