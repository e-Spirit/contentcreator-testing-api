package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import java.util.List;
import org.openqa.selenium.WebElement;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report.input.CCInputEntry;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import de.espirit.firstspirit.webedit.test.ui.exception.CCInputNotPresentException;

/**
 * Accesses common actions and properties provides by every report.
 */
public interface Report extends Web {

	/**
	 * Returns how many results the report has found.
	 *
	 * @return the result count
	 */
	int getResultCount() throws CCAPIException;


	/**
	 * Returns the html of the given report entry.
	 *
	 * @param pos number of the report entry (0 = first entry, 1 = second one, ...)
	 * @return report entry.
	 */
	WebElement getEntry(final int pos) throws CCAPIException;


	/**
	 * Create a query and execute it.
	 *
	 * @param inputs The input for the query.
	 * @throws CCAPIException a generic error
	 * @throws CCInputNotPresentException thrown when there is no specified input field
	 */
	void query(List<CCInputEntry<?>> inputs) throws CCAPIException, CCInputNotPresentException;


	/**
	 * Toggles the visibility of a report (expands it or collapses it, depending on the current state).
	 */
	void toggle();


	/**
	 * Restarts a report.
	 */
	void reload() throws CCAPIException;


	/**
	 * Returns a list of all parameters within the report
	 *
	 * @return the parameters within the report
	 */
	List<CCInputComponent> parameters() throws CCAPIException;
}
