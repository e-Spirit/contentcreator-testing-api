package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent.CCInputComponent;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Accesses common actions and properties provides by every report.
 */
public interface Report extends Web {

    /**
     * Returns how many results the report has found.
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
     * Toggles the visibility of a report (expands it or collapses it, depending on the current state).
     */
    void toggle();

    /**
     * Restarts a report.
     */
    void reload() throws CCAPIException;

    /**
     * Returns a list of all parameters within the report
     * @return the parameters within the report
     */
    List<CCInputComponent> parameters() throws CCAPIException;
}
