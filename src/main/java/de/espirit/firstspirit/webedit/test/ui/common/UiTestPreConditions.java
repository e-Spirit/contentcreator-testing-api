package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.project.Project;
import de.espirit.firstspirit.access.store.ContentProducer;
import de.espirit.firstspirit.access.store.sitestore.StartNode;
import de.espirit.firstspirit.store.access.AccessImplUtil;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.MenuBar;
import de.espirit.firstspirit.webedit.test.ui.Report;
import de.espirit.firstspirit.webedit.test.ui.SearchReport;
import de.espirit.firstspirit.webedit.test.ui.WE;

import org.junit.Test;

import static de.espirit.firstspirit.access.store.Previewable.PREVIEWMODE_FULL_QUALIFIED;
import static de.espirit.firstspirit.access.store.Previewable.PREVIEWMODE_WEBEDIT;


/**
 * Tests all elements of the UI {@link WE HTML adapter api}.
 */
public class UiTestPreConditions extends AbstractUiTest {

	@Test
	public void menu() throws Exception {
		assertNotNull("menu not found!", we().menu().html());
		assertNotSame("element-status must not be UNKNOWN!", MenuBar.ElementStatus.UNKNOWN, we().menu().getElementStatus());
		we().menu().search("solar"); // search must not throw any exceptions
	}


	@Test
	public void preview() throws Exception {
		final StartNode element = getStartNode();
		final Project project = element.getProject();
		final String url = AccessImplUtil.getPreviewUrl(project.getUserService(), (ContentProducer) element, project.getMasterLanguage(), project.getWebEditTemplateSet(), false, PREVIEWMODE_FULL_QUALIFIED | PREVIEWMODE_WEBEDIT, null);
		final int previewPosition = url.indexOf("/preview/");

		assertNotNull("preview not found!", we().preview().html());			// TEST 1: html
		we().preview().reload();                                        	// TEST 2: test reload
		we().preview().setUrl(url);                                     	// TEST 3: setting a preview url
		assertTrue("wrong preview.url!", we().preview().getUrl().endsWith(url.substring(previewPosition)));   // TEST 4: getting the preview url
	}


	@Test
	public void reportsBookmarks() throws Exception {
		final Report report = we().reports().bookmarks();

		assertNotNull("bookmark report not found!", report.html());		// TEST 1: html
		report.toggle();                                              	// TEST 2: access toggle button
		report.getResultCount();                                        // TEST 3: access result-count
		report.reload();												// TEST 4: access reload button
	}


	@Test
	public void reportsHistory() throws Exception {
		final Report report = we().reports().history();

		assertNotNull("history report not found!", report.html());		// TEST 1: html
		report.toggle();                                              	// TEST 2: access toggle button
		report.getResultCount();                                        // TEST 3: access result-count
		report.reload();												// TEST 4: access reload button
	}


	@Test
	public void reportsMessages() throws Exception {
		final Report report = we().reports().messages();

		assertNotNull("history report not found!", report.html());		// TEST 1: html
		report.toggle();                                              	// TEST 2: access toggle button
		report.getResultCount();                                        // TEST 3: access result-count
		report.reload();												// TEST 4: access reload button
	}


	@Test
	public void reportsRelations() throws Exception {
		final Report report = we().reports().relations();

		assertNotNull("relations report not found!", report.html());	// TEST 1: html
		report.toggle();                                              	// TEST 2: access toggle button
		report.getResultCount();                                        // TEST 3: access result-count
		report.reload();												// TEST 4: access reload button
	}


	@Test
	public void reportsSearch() throws Exception {
		final SearchReport report = we().reports().search();

		assertNotNull("search report not found!", report.html());		// TEST 1: html
		report.toggle();                                              	// TEST 2: access toggle button
		report.setParamMyElements(false);                             	// TEST 3: access parameter "my elments"
		report.getResultCount();                                        // TEST 4: access result-count
		report.reload();												// TEST 5: access reload button
	}

	@Test
	public void reportsWorkflows() throws Exception {
		final Report report = we().reports().workflows();

		assertNotNull("workflow report not found!", report.html());		// TEST 1: html
		report.toggle();                                              	// TEST 2: access toggle button
		report.getResultCount();                                        // TEST 3: access result-count
		report.reload();												// TEST 4: access reload button
	}
}
