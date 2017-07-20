package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.Constants;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import de.espirit.firstspirit.webedit.test.ui.util.Assert;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalChromeWebDriverFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

@UiTestRunner.WebDriver({LocalChromeWebDriverFactory.class})
public class ExampleTest extends AbstractUiTest {
    private static final Logger LOGGER = Logger.getLogger(ExampleTest.class);

    @Test
    public void search() {
        //System.setProperty(Constants.PARAM_ERROR_FILE_PATH, "d:\\screenshots\\");
        testThis();

        this.switchProject("Geberit Country");

        testThis();
    }

    private void testThis() {
        ExampleTest.LOGGER.info("Test 'search for solar' started");
        this.cc().menu().search("test");
        Assert.assertTrue("search-results expected", this.cc().reports().search().getResultCount() > 0, this.cc().driver());

        ExampleTest.LOGGER.info("Test 'search for solar' successful");
    }
}