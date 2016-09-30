package de.espirit.firstspirit.webedit.test.ui;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;


/**
* Creates a remote Internet Explorer instance on our UI test vm. <p>
 * The url can be configured with -D property {@code "webdriverHost.ie"}, default value
 * {@code "webdriverHost"}, default value {@link #DEFAULT_REMOTE_HOST}.
*/
public class RemoteIEWebDriverFactory implements WebDriverFactory {

	private static final String DEFAULT_REMOTE_HOST = "http://localhost:4444/wd/hub";

	@Override
	public RemoteWebDriver createWebDriver() throws IOException {
		final String webdriverHost = System.getProperty("webdriverHost.ie", System.getProperty("webdriverHost", DEFAULT_REMOTE_HOST));
		final DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		return new RemoteWebDriver(new URL(webdriverHost), capabilities);
	}


	@Override
	public String getName() {
		return "Internet Explorer";
	}

}
