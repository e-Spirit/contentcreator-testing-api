package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.pagestore.Page;
import de.espirit.firstspirit.access.store.pagestore.Section;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.client.EditorIdentifier;
import de.espirit.firstspirit.common.FileUtilities;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.Preview;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collection;
import java.util.List;


/**
 * WebEdit UI test for easy-edit actions inside the preview.
 */
public class UiTestDragDrop extends AbstractUiTest {

	/**
	 * Tests if a section can be moved by drag and drop.
	 */
	@Test
	public void moveSection() throws Exception {
		// go to start-node
		final PageRef element = getStartNode();
		final String url = toPreviewUrl(element);
		we().preview().setUrl(url);

		// PRE-CONDITION: element has actions
		final Page page = element.getPage();
		assertNotNull("[pre-condition] no page found!", page);
		final List<Section> sections = page.getChildren(Section.class, true).toList();
		final Section<?> section1 = sections.get(0);
		assertNotNull("[pre-condition] page has no section!", section1);
		final long revisionId = section1.getRevision().getId();
		final EditorIdentifier identifier = EditorIdentifier.on(section1.getId(), Store.Type.PAGESTORE).build();
		final Collection<Preview.Action> actions = we().preview().actionsOf(identifier);
		assertNotNull("[pre-condition] section not found in preview!", actions);
		assertTrue("[pre-condition] section has no actions!", !actions.isEmpty());

		we().driver().switchTo().frame(we().preview().html());
		final Section<?> section2 = sections.get(sections.size() - 1);
		final EditorIdentifier identifier2 = EditorIdentifier.on(section2.getId(), Store.Type.PAGESTORE).build();
		final WebElement section2element = we().driver().findElement(By.cssSelector("[data-fs-id=\"" + identifier2.getId() + "\"]"));

		try {
			final Preview.Action action = actions.iterator().next(); // first action is section-dnd-move action
			((RemoteWebDriver) we().driver()).executeScript(
					FileUtilities.inputStreamToString(UiTestDragDrop.class.getResourceAsStream("UiTestDragDrop.js"), "UTF-8"),
					action.html(),
					section2element
			);
			Thread.sleep(1000);
		} finally {
			we().driver().switchTo().defaultContent();
		}

		// TEST: check whether section was changed
		section1.refresh();
		assertTrue("section wasn't moved!", section1.getRevision().getId() != revisionId);
	}
}
