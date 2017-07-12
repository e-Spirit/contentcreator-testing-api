package de.espirit.firstspirit.webedit.test.ui.webdriver.factory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;

/**
 * Creates a local chrome instance.
 * <p>
 * The chrome executable can be configured with -D property {@code "webdriver.executable"}, default value {@link #DEFAULT_WEBDRIVER_EXECUTABLE}.
 */
public class LocalChromeWebDriverFactory implements WebDriverFactory {
    private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Entwicklung\\Chromedriver\\chromedriver.exe";
    private static final String DEFAULT_VERBOSITY = "false";

    @Override
    public RemoteWebDriver createWebDriver() throws IOException {
        final String webdriverExecutable = env("webdriver.chrome.executable", DEFAULT_WEBDRIVER_EXECUTABLE);
        final boolean verbose = Boolean.valueOf(env("webdriver.chrome.verbose", DEFAULT_VERBOSITY));
        final ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(webdriverExecutable)).withVerbose(verbose).build();

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

        return new ChromeDriver(service, capabilities);
    }

    @Override
    public String getName() {
        return "Google Chrome";
    }

}
