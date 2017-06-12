package de.espirit.firstspirit.webedit.test.ui.webdriver.factory;


import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;



/**
 * Creates a local ie instance.
 * <p>
 * The ie executable can be configured with -D property {@code "webdriver.ie.executable"}, default value {@link #DEFAULT_WEBDRIVER_EXECUTABLE}.
 */
public class LocalIEWebDriverFactory implements WebDriverFactory {
	
	private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Development\\IntelliJ\\E-Spirit\\ContentCreatorTesting\\testing\\driver\\IEDriverServer.exe";
	
	
	@Override
	public RemoteWebDriver createWebDriver() throws IOException {
		final String              webdriverExecutable = env("webdriver.ie.executable", DEFAULT_WEBDRIVER_EXECUTABLE);
		final InternetExplorerDriverService service             = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(webdriverExecutable)).build();
		return new InternetExplorerDriver(service);
	}
	
	
	@Override
	public String getName() {
		return "Internet Explorer";
	}
	
}
