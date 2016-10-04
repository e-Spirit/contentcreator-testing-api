package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.manager.SearchManager;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.webdriver.factory.LocalPhantomJSWebDriverFactory;
import de.espirit.firstspirit.webedit.test.ui.UiTestRunner;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

@UiTestRunner.WebDriver({LocalPhantomJSWebDriverFactory.class})
public class UiTestSearchReport extends AbstractUiTest {

    private static final long INDEX_WAIT_TIMEOUT = TimeUnit.MINUTES.toMillis(5);    // maximum time to wait until the search index should be ready-to-use
    private static final long INDEX_WAIT_SLEEP = TimeUnit.SECONDS.toMillis(1);        // wait 1s for the next index check

    /**
     * Tests the access to the search-result entries and that the report-message vanishes.
     */
    @Test
    public void accessingSearchResults() throws Exception {
        // PRE-CONDITION: wait until indexing is finished
        waitForIndex();

        // TEST: search and check number of results
        we().menu().search("solar");
        Thread.sleep(1000);
        assertTrue("results expected!", we().reports().search().getResultCount() > 0);                // TEST 1: result-count
        assertNotNull("1st result entry must not be null!", we().reports().search().getEntry(0));    // TEST 2: result entries
        assertNull("report message must be invisible!", we().reports().search().reportMessage());   // TEST 3: report-message
    }

    /**
     * After changing the search parameters which leads to "no results", the old result entries have to be removed!
     *
     * @see <a href="http://ts/156067">TS#156067</a>
     */
    @Test
    public void oldResultEntriesMustBeRemoved_Bug156067() throws Exception {
        // PRE-CONDITION: search with results found
        waitForIndex();
        we().menu().search("solar");
        Thread.sleep(1000);
        assertTrue("pre-condition: no results found!", we().reports().search().getResultCount() > 0);

        // TEST: search with "no results" => old entries must be removed
        we().menu().search("whiofdoifehfhioweofhwoeifhwehiof");
        Thread.sleep(1000);
        assertEquals("no results expected!", 0, we().reports().search().getResultCount());
        try {
            final WebElement entry = we().reports().search().getEntry(0);
            fail("unexpected entry available: " + entry);
        } catch (final NoSuchElementException expected) {
            // expected exception
        }
        final String reportMessage = we().reports().search().reportMessage();
        assertNotNull("report-message must not be null!", reportMessage);
        assertTrue("wrong report-message: \"" + reportMessage + "\" !", reportMessage.contains("no results") || reportMessage.contains("keine Treffer"));
    }

    /**
     * Wait until the search index is ready-to-use.
     */
    private void waitForIndex() {
        final long timeout = System.currentTimeMillis() + INDEX_WAIT_TIMEOUT;
        final SearchManager sm = fs().connection().getManager(SearchManager.class);
        while (sm.getState(we().project().getId()) != SearchManager.IndexingState.INDEXED && System.currentTimeMillis() < timeout) {
            try {
                Thread.sleep(INDEX_WAIT_SLEEP);
            } catch (final InterruptedException e) {
                throw new RuntimeException("waiting for search index interrupted!", e);
            }
        }
    }

}
