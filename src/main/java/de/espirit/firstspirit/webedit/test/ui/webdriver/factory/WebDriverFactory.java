package de.espirit.firstspirit.webedit.test.ui.webdriver.factory;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;

/**
 * Factory for {@link RemoteWebDriver}.
 */
public interface WebDriverFactory {

    RemoteWebDriver createWebDriver() throws IOException;

    String getName();
}
