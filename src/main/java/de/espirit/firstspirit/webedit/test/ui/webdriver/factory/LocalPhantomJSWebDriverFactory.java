package de.espirit.firstspirit.webedit.test.ui.webdriver.factory;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;

public class LocalPhantomJSWebDriverFactory implements WebDriverFactory {
    private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Entwicklung\\phantomjs\\bin\\phantomjs.exe";

    @Override
    public RemoteWebDriver createWebDriver() throws IOException {
        final String webdriverExecutable = env("webdriver.phantomjs.executable", DEFAULT_WEBDRIVER_EXECUTABLE);

        LogManager.getLogManager().reset();
        final java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
        final String[] phantomArgs = {"--webdriver-loglevel=NONE"};
        PhantomJSDriverService service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(new java.io.File(webdriverExecutable)).usingAnyFreePort().usingCommandLineArguments(phantomArgs).build();
        service.start();

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

        return new PhantomJSDriver(service, capabilities);
    }

    @Override
    public String getName() {
        return "Google Chrome";
    }
}
