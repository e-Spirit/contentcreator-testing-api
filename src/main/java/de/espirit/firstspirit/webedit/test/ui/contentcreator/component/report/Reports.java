package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.report;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import org.jetbrains.annotations.NotNull;

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
    SearchReport search();

    /**
     * Returns the bookmarks report.
     *
     * @return Bookmarks report
     */
    @NotNull
    Report bookmarks();

    /**
     * Returns the workflow report.
     *
     * @return Workflow report
     */
    @NotNull
    Report workflows();

    /**
     * Returns the history report.
     *
     * @return History report
     */
    @NotNull
    Report history();

    /**
     * Returns the relations report.
     *
     * @return Relations report
     */
    @NotNull
    Report relations();

    /**
     * Returns the messages report.
     *
     * @return Messages report
     */
    @NotNull
    Report messages();

    /**
     * Returns the N-th custom report.
     *
     * @param no number of the custom report (0 = first one, 1 = second one, ...).
     * @return custom report.
     */
    @NotNull
    Report custom(final int no);

    /**
     * Returns the custom report by given display name
     * @param displayName The display name of the report
     * @return Returns the report
     */
    @NotNull
    Report customByName(final String displayName);
}
