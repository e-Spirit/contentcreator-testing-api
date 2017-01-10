package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import org.jetbrains.annotations.NotNull;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

/**
 * Provides access to the report area with it's different reports.
 */
public interface Reports extends Web {

	/**
	 * Returns the search report.
	 *
	 * @return Search report.
	 */
	@NotNull
	SearchReport search() throws CCAPIException;


	/**
	 * Returns the bookmarks report.
	 *
	 * @return Bookmarks report
	 */
	@NotNull
	Report bookmarks() throws CCAPIException;


	/**
	 * Returns the workflow report.
	 *
	 * @return Workflow report
	 */
	@NotNull
	Report workflows() throws CCAPIException;


	/**
	 * Returns the history report.
	 *
	 * @return History report
	 */
	@NotNull
	Report history() throws CCAPIException;


	/**
	 * Returns the relations report.
	 *
	 * @return Relations report
	 */
	@NotNull
	Report relations() throws CCAPIException;


	/**
	 * Returns the messages report.
	 *
	 * @return Messages report
	 */
	@NotNull
	Report messages() throws CCAPIException;


	/**
	 * Returns the N-th custom report.
	 *
	 * @param no number of the custom report (0 = first one, 1 = second one, ...).
	 * @return custom report.
	 */
	@NotNull
	Report custom(final int no) throws CCAPIException;


	/**
	 * Returns the custom report by given display name
	 *
	 * @param localizedDisplayName The localized display name of the report
	 * @return Returns the report
	 */
	@NotNull
	Report customByName(final String localizedDisplayName) throws CCAPIException;
}
