package de.espirit.firstspirit.webedit.test;

import de.espirit.firstspirit.access.schedule.ScheduleEntryState;
import de.espirit.firstspirit.access.schedule.TaskResult;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.util.Assert;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalPhantomJSWebDriverFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

@UiTestRunner.WebDriver({
		LocalPhantomJSWebDriverFactory.class
})
public class ExampleTest extends AbstractUiTest {
	private static final Logger LOGGER = Logger.getLogger(ExampleTest.class);

	@Test
	public void search() {
		System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
		LOGGER.info("Test 'search for solar' started");
		cc().menu().search("solar");
		Assert.assertTrue("search-results expected", cc().reports().search().getResultCount() > 0, cc().driver());

		LOGGER.info("Test 'search for solar' successful");
	}

	@Test
	public void startDeployment() {
		System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
		LOGGER.info("Test 'start deployment' started");
		LOGGER.info("Deployment started");
		final ScheduleEntryState deployment = fs().deploy("Deployment");
		LOGGER.info("Checking results");
		for (TaskResult taskResult : deployment.getTaskResults()) {
			LOGGER.info("*** " + taskResult.getTask().getName() + " ***");
			if(taskResult.getFatalErrorCount() > 0)
				LOGGER.info("fatal errors : " + taskResult.getFatalErrorCount());
			LOGGER.info("errors       : " + taskResult.getErrorCount());
			LOGGER.info("warnings     : " + taskResult.getWarningCount());
			final int errorSum = taskResult.getFatalErrorCount() + taskResult.getErrorCount() + taskResult.getWarningCount();
			Assert.assertTrue("", errorSum == 0, cc().driver());
		}

		LOGGER.info("Test 'start deployment' successful");
	}
}
