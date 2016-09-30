package de.espirit.firstspirit.webedit.test.ui.common;

import de.espirit.firstspirit.access.store.ElementDeletedException;
import de.espirit.firstspirit.access.store.IDProvider;
import de.espirit.firstspirit.access.store.LockException;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.storage.Revision;
import de.espirit.firstspirit.webedit.test.ui.AbstractUiTest;
import de.espirit.firstspirit.webedit.test.ui.MenuBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Ignore;
import org.junit.Test;

import java.util.EnumSet;


public class UiTestElementStatus extends AbstractUiTest {

	/**
	 * Test: element status must not be {@link MenuBar.ElementStatus#UNKNOWN unknown}.
	 *
	 * @see MenuBar.ElementStatus
	 */
	@Test
	public void notUnknown() throws Exception {
		assertNotSame("element-status must not be UNKNOWN: " + we().menu().getElementStatus(), MenuBar.ElementStatus.UNKNOWN, we().menu().getElementStatus());
	}


	/**
	 * Tests if the element status follow changes of the appropriate element.
	 */
	@Test
	public void changes() throws Exception {
		// SETUP: ensure element is released
		final PageRef startPageRef = getStartNode();
		releaseElement(startPageRef.getPage());
		releaseElement(startPageRef);

		// PRE-CONDITION: go to "element" and ensure that it's released
		final String url = toPreviewUrl(startPageRef);
		we().preview().setUrl(url);
		assertEquals("pre-condition: wrong element-status!", MenuBar.ElementStatus.RELEASED, we().menu().getElementStatus());

		// TEST 1: change the element and check the element-status
		changeElement(startPageRef);

		try {
			we().preview().reload();
			assertEquals("changed element: wrong element-status!", MenuBar.ElementStatus.CHANGED, we().menu().getElementStatus());
		} finally {
			releaseElement(startPageRef);
		}

		// TEST 2: check element-status after releasing the element
		we().preview().reload();
		assertEquals("released element: wrong element-status!", MenuBar.ElementStatus.RELEASED, we().menu().getElementStatus());
	}


	/**
	 * Tests the element status of a reverted element.
	 */
	@Test
	@Ignore
	public void revert_Bug156438() throws Exception {
		final IDProvider element = getStartNode();
		final IDProvider parent = element.getParent();
		assertNotNull("pre-condition: parent must not be null!", parent);
		assertTrue("pre-condition: start-node must be a PageRef!", element instanceof PageRef);

		// PRE-CONDITION: go to "element" and ensures a known element status
		we().preview().setUrl(toPreviewUrl(element));
		assertNotSame("pre-condition: element-status must not be UNKNOWN!", MenuBar.ElementStatus.UNKNOWN, we().menu().getElementStatus());

		// SETUP: delete and revert element
		final Revision oldRevision = parent.getRevision();
		element.setLock(true);
		element.delete();

		we().preview().reload();

		parent.revert(oldRevision, true, EnumSet.noneOf(IDProvider.RevertType.class));

		// TEST: status mut not be UNKNOWN
		we().preview().reload();
		assertNotSame("element-status must not be UNKNOWN!", MenuBar.ElementStatus.UNKNOWN, we().menu().getElementStatus());
	}


	private void releaseElement(@Nullable final IDProvider element) throws LockException, ElementDeletedException {
		if (element != null) {
			element.setLock(true);
			try {
				element.release();
			} finally {
				element.setLock(false);
			}
			assertTrue("element must be released!", element.isReleased());
		}
	}


	private void changeElement(@NotNull final IDProvider element) throws LockException, ElementDeletedException {
		element.setLock(true);
		try {
			element.save("UiTestElementStatus.changes()", true);
		} finally {
			element.setLock(false);
		}
		assertFalse("element must not be released!", element.isReleased());
	}
}
