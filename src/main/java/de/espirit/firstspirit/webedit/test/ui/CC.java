package de.espirit.firstspirit.webedit.test.ui;

import de.espirit.firstspirit.access.project.Project;

import de.espirit.firstspirit.webedit.test.ui.webedit.component.menu.MenuBar;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.preview.Preview;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.report.Reports;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.Web;
import org.openqa.selenium.WebDriver;

/**
 * Provides access to WebEdit's UI elements like the {@link #menu() MenuBar}, {@link #preview() Preview}
 * and {@link #reports() Reports}.
 *
 * @see MenuBar
 * @see Preview
 * @see Reports
 */
public interface CC extends Web {

    /**
     * Returns access to WebEdit's menu bar and search field.
     *
     * @return menu bar.
     */
    MenuBar menu();

    /**
     * Returns access to the preview pane.
     *
     * @return preview pane.
     */
    Preview preview();

    /**
     * Returns access to the report area.
     *
     * @return report area.
     */
    Reports reports();

    /**
     * Reloads WebEdit completely.
     *
     * @see Preview#reload()
     */
    void reload();

    /**
     * Returns the internal {@code WebDriver} that controls the WebEdit instance.
     *
     * @return {@code WebDriver} that controls the WebEdit instance.
     */
    WebDriver driver();

    /**
     * Returns the initial project.
     *
     * @return initial project.
     */
    Project project();

    /**
     * Logs out current session.
     */
    void logout();
}