package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.pagestore.Page;
import de.espirit.firstspirit.access.store.pagestore.Section;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.client.EditorIdentifier;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.webedit.component.preview.Preview;

import org.junit.Test;

import java.util.Collection;

/**
 * WebEdit UI test for easy-edit actions inside the preview.
 */
public class UiTestEasyEditActions extends AbstractUiTest {

    /**
     * Tests if actions are available for a section.
     */
    @Test
//	@Ignore
    public void actionsAvailable_section() throws Exception {
        final PageRef element = getStartNode();
        final String url = toPreviewUrl(element);

        // PRE-CONDITION: go to page-ref
        we().preview().setUrl(url);

        // TEST: element has actions
        final Page page = element.getPage();
        assertNotNull("[pre-condition] no page available!", page);

        final Section<?> section = page.getChildren(Section.class, true).getFirst();
        assertNotNull("[pre-condition] page has no sections!", section);

        final EditorIdentifier identifier = EditorIdentifier.on(section.getId(), Store.Type.PAGESTORE).build();
        final Collection<Preview.Action> actions = we().preview().actionsOf(identifier);
        assertNotNull("section not found in preview!", actions);
        assertTrue("no actions found for section!", !actions.isEmpty());
    }
}
