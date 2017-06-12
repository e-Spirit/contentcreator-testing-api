package de.espirit.firstspirit.webedit.test.ui.webdriver.factory;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

import static de.espirit.firstspirit.webedit.test.ui.util.Utils.env;



/**
 * Creates a local chrome instance.
 * <p>
 * The chrome executable can be configured with -D property {@code "webdriver.chrome.executable"}, default value {@link #DEFAULT_WEBDRIVER_EXECUTABLE}.
 */
public class LocalChromeWebDriverFactory implements WebDriverFactory {
	
	// TODO temporary changed
//    private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Programme\\Chromedriver\\chromedriver.exe";
	private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Development\\IntelliJ\\E-Spirit\\ContentCreatorTesting\\testing\\driver\\chromedriver.exe";
	private static final String DEFAULT_VERBOSITY            = "false";
	
	
	@Override
	public RemoteWebDriver createWebDriver() throws IOException {
		final String              webdriverExecutable = env("webdriver.chrome.executable", DEFAULT_WEBDRIVER_EXECUTABLE);
		final boolean             verbose             = Boolean.valueOf(env("webdriver.chrome.verbose", DEFAULT_VERBOSITY));
		final ChromeDriverService service             = new ChromeDriverService.Builder().usingDriverExecutable(new File(webdriverExecutable)).withVerbose(verbose).build();
		return new ChromeDriver(service);
	}
	
	
	@Override
	public String getName() {
		return "Google Chrome";
	}
	
}
