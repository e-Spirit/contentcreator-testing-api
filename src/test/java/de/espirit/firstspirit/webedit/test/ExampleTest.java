package de.espirit.firstspirit.webedit.test;

import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.Report;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalPhantomJSWebDriverFactory;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@UiTestRunner.WebDriver({
		LocalPhantomJSWebDriverFactory.class
})
public class ExampleTest extends AbstractUiTest {
	private static final Logger LOGGER = Logger.getLogger(ExampleTest.class);

	@BeforeClass
	public static void setup() {
		System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
	}

	@Test
	public void executeTestcase() {
		LOGGER.info("Test 'testVideoManagementReport' started");
		LOGGER.info("action-menu is shown | 'VideoManagement-Report' is available | report can be opened | report contains at least one entry");

		Report videoManagementReport = cc().reports().customByName("VideoManagement Pro");

		Assert.assertNotNull(videoManagementReport);
		LOGGER.info("'VideoManagement-Report' is available");
		videoManagementReport.toggle();
		final WebElement firstEntry = videoManagementReport.getEntry(0);
		final WebElement reportEntryTitle = firstEntry.findElement(By.cssSelector("div.report-entry-title"));
		LOGGER.info("Title of first entry: '" + reportEntryTitle.getText() + '\'');
		Assert.assertNotNull(reportEntryTitle);

		LOGGER.info("Test 'testVideoManagementReport' successful");
	}
}
