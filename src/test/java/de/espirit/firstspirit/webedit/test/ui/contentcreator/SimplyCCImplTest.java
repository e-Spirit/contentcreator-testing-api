package de.espirit.firstspirit.webedit.test.ui.contentcreator;


import de.espirit.firstspirit.access.schedule.ScheduleEntryState;
import de.espirit.firstspirit.access.schedule.TaskResult;
import de.espirit.firstspirit.webedit.test.ui.AbstractSimplyUiTest;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.loginhook.SimplyCCLoginHook;
import de.espirit.firstspirit.webedit.test.ui.util.Assert;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalChromeWebDriverFactory;
import org.apache.log4j.Logger;
import org.junit.Test;



@UiTestRunner.WebDriver({LocalChromeWebDriverFactory.class})
@UiTestRunner.UseLoginHook(SimplyCCLoginHook.class)
public class SimplyCCImplTest extends AbstractSimplyUiTest {
	
	private static final Logger LOGGER = Logger.getLogger(SimplyCCImplTest.class);
	
	@Test
	public void search() {
		System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
		SimplyCCImplTest.LOGGER.info("Test 'search for solar' started");
		this.cc().menu().search("solar");
		Assert.assertTrue("search-results expected", this.cc().reports().search().getResultCount() > 0, this.cc().driver());
		SimplyCCImplTest.LOGGER.info("Test 'search for solar' successful");
	}
	
	//@Test
	public void startDeployment() {
		System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
		SimplyCCImplTest.LOGGER.info("Test 'start deployment' started");
		SimplyCCImplTest.LOGGER.info("Deployment started");
		final ScheduleEntryState deployment = null;//this.fs().deploy("Deployment");
		SimplyCCImplTest.LOGGER.info("Checking results");
		for (final TaskResult taskResult : deployment.getTaskResults()) {
			SimplyCCImplTest.LOGGER.info("*** " + taskResult.getTask().getName() + " ***");
			if (taskResult.getFatalErrorCount() > 0) {
				SimplyCCImplTest.LOGGER.info("fatal errors : " + taskResult.getFatalErrorCount());
			}
			SimplyCCImplTest.LOGGER.info("errors       : " + taskResult.getErrorCount());
			SimplyCCImplTest.LOGGER.info("warnings     : " + taskResult.getWarningCount());
			final int errorSum = taskResult.getFatalErrorCount() + taskResult.getErrorCount() + taskResult.getWarningCount();
			Assert.assertTrue("", errorSum == 0, this.cc().driver());
		}
		
		SimplyCCImplTest.LOGGER.info("Test 'start deployment' successful");
	}
}
