package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import org.jetbrains.annotations.NotNull;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

/**
 * Provides access to WebEdit's menu bar with it's element status, it's actions/content menu and the search capabilities.
 */
public interface MenuBar extends Web {

	/**
	 * Types of element status ({@link #CHANGED}, {@link #IN_WORKFLOW}, {@link #RELEASED}, {@link #UNKNOWN}).
	 */
	enum ElementStatus {
		ARCHIVED, CHANGED, DELETED, IN_WORKFLOW, RELEASED, UNKNOWN;
	}


	/**
	 * Returns the element status shown in WebEdit.
	 *
	 * @return element status.
	 */
	@NotNull
	ElementStatus getElementStatus() throws CCAPIException;


	/**
	 * Waits for a specified element status
	 *
	 * @param elementStatus The element status to wait for
	 */
	void waitForElementStatus(MenuBar.ElementStatus elementStatus);


	/**
	 * Returns the workflow menu
	 *
	 * @return WorkflowMenu
	 */
	@NotNull
	Menu workflowMenu();


	/**
	 * Returns the action menu shown in WebEdit.
	 *
	 * @return action menu
	 */
	@NotNull
	Menu actionMenu();


	/**
	 * Returns the content menu shown in WebEdit.
	 *
	 * @return content menu
	 */
	@NotNull
	Menu contentMenu();


	/**
	 * Starts a search of the given query string.
	 *
	 * @param query text to search.
	 */
	void search(final String query) throws CCAPIException;


	/**
	 * Returns the number of search results.
	 *
	 * @return number of search results, when there is no result you will get -1
	 */
	int getSearchResultCount();

}
