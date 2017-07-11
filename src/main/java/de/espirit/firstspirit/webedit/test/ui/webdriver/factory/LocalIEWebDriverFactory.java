package de.espirit.firstspirit.webedit.test.ui.webdriver.factory;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;

/**
 * Creates a local Internet Explorer webdriver instance <p>
 */
public class LocalIEWebDriverFactory implements WebDriverFactory {
    private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Entwicklung\\IEDriverServer\\IEDriverServer.exe";

    @Override
    public RemoteWebDriver createWebDriver() throws IOException {
        final String webdriverExecutable = env("webdriver.ie.executable", DEFAULT_WEBDRIVER_EXECUTABLE);
        final InternetExplorerDriverService service = new InternetExplorerDriverService.Builder()
                .usingDriverExecutable(new File(webdriverExecutable)).build();

        final DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        return new InternetExplorerDriver(service, capabilities);
    }

    @Override
    public String getName() {
        return "Internet Explorer";
    }
}