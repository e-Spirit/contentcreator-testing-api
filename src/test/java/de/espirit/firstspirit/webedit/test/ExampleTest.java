package de.espirit.firstspirit.webedit.test;

import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.util.Assert;
import de.espirit.firstspirit.webedit.test.ui.util.Utils;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalPhantomJSWebDriverFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

@UiTestRunner.WebDriver({
		LocalPhantomJSWebDriverFactory.class
})
public class ExampleTest extends AbstractUiTest {
	private static final Logger LOGGER = Logger.getLogger(ExampleTest.class);

	@Test
	public void executeTestcase() {
		System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
		LOGGER.info("Test 'search for solar' started");
		Utils.waitForCC();

		cc().menu().search("solar");
		Assert.assertTrue("search-results expected", cc().reports().search().getResultCount() > 0, cc().driver());

		LOGGER.info("Test 'search for solar' successful");
	}
}
