package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import de.espirit.firstspirit.access.schedule.ScheduleEntryState;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.io.ServerConnection;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.component.FSProject;

/**
 * Provides access to the appropriate FirstSpirit server, see {@link #connection()}.
 */
public interface FS {

    /**
     * Returns the FirstSpirit connection.
     *
     * @return FirstSpirit connection.
     */
    ServerConnection connection();

    /**
     * Creates a page.
     * @return FirstSpirit pageref.
     */
    PageRef createPage(final String name, final String pageTemplateUid, final String targetPageFolder);

	/**
	 * Returns a link to the FirstSpirit project.
	 * @return FirstSpirit project.
	 */
	FSProject project();
	
	
	/**
	 * Returns name of the FirstSpirit project.
	 * @return Project name.
	 */
	String getProjectName();

	/**
	 * Starts a deployment.
	 * @return FirstSpirit scheduleentrystate.
	 */
	ScheduleEntryState deploy(String deployment);
}
