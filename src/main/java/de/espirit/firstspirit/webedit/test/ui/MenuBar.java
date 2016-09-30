package de.espirit.firstspirit.webedit.test.ui;

import org.jetbrains.annotations.NotNull;


/**
 * Provides access to WebEdit's menu bar with it's element status, it's actions and the search capabilities.
 */
public interface MenuBar extends Web {

	/**
	 * Types of element status ({@link #CHANGED}, {@link #IN_WORKFLOW}, {@link #RELEASED}, {@link #UNKNOWN}).
	 */
	enum ElementStatus {
		ARCHIVED,
		CHANGED,
		DELETED,
		IN_WORKFLOW,
		RELEASED,
		UNKNOWN;
	}


	/**
	 * Returns the element status shown in WebEdit.
	 *
	 * @return element status.
	 */
	@NotNull
	ElementStatus getElementStatus();


	/**
	 * Starts a search of the given query string.
	 *
	 * @param query text to search.
	 */
	void search(final String query);

}
