package de.espirit.firstspirit.webedit.test.ui;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;


/**
 * Creates a remote Chrome instance on our UI test vm.
 * <p>
 * The url can be configured with -D property {@code "webdriverHost.chrome"}, default value
 * {@code "webdriverHost"}, default value {@link #DEFAULT_REMOTE_HOST}.
*/
public class RemoteChromeWebDriverFactory implements WebDriverFactory {

	private static final String DEFAULT_REMOTE_HOST = "http://localhost:4444/wd/hub";

	@Override
	public RemoteWebDriver createWebDriver() throws IOException {
		final String webdriverHost = System.getProperty("webdriverHost.chrome", System.getProperty("webdriverHost", DEFAULT_REMOTE_HOST));
		return new RemoteWebDriver(new URL(webdriverHost), DesiredCapabilities.chrome());
	}


	@Override
	public String getName() {
		return "Google Chrome";
	}

}
