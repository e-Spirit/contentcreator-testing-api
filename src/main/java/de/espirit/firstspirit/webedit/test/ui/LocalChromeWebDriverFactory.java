package de.espirit.firstspirit.webedit.test.ui;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;


/**
 * Creates a local chrome instance.
 * <p>
 * The chrome executable can be configured with -D property {@code "webdriver.executable"}, default value {@link #DEFAULT_WEBDRIVER_EXECUTABLE}.
*/
public class LocalChromeWebDriverFactory implements WebDriverFactory {

	private static final String DEFAULT_WEBDRIVER_EXECUTABLE = "D:\\Programme\\Chromedriver\\chromedriver.exe";


	@Override
	public RemoteWebDriver createWebDriver() throws IOException {
		final String webdriverExecutable = System.getProperty("webdriver.executable", DEFAULT_WEBDRIVER_EXECUTABLE);
		final ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(webdriverExecutable)).withVerbose(true).build();
		return new ChromeDriver(service);
	}


	@Override
	public String getName() {
		return "Google Chrome";
	}

}
