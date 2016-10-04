package de.espirit.firstspirit.webedit.test.ui.webedit.component.report;

import de.espirit.firstspirit.webedit.test.ui.webedit.component.Web;
import org.openqa.selenium.WebElement;

/**
 * Accesses common actions and properties provides by every report.
 */
public interface Report extends Web {

    /**
     * Returns how many results the report has found.
     */
    int getResultCount();

    /**
     * Returns the html of the given report entry.
     *
     * @param pos number of the report entry (0 = first entry, 1 = second one, ...)
     * @return report entry.
     */
    WebElement getEntry(final int pos);

    /**
     * Toggles the visibility of a report (expands it or collapses it, depending on the current state).
     */
    void toggle();

    /**
     * Restarts a report.
     */
    void reload();
}
