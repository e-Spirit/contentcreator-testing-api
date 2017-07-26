package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import de.espirit.firstspirit.access.Connection;
import de.espirit.firstspirit.access.schedule.ScheduleEntryState;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.agency.SpecialistsBroker;
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
    Connection connection();

    /**
     * Creates a page and a pageFolder with the same name as parent.
     * Also creates a pageRef and a pageRefFolder with the same structure.
     *
     * @param name             Uid of the page and pageFolder to be created
     * @param pageTemplateUid  Page template to be used for creation
     * @param targetPageFolder Uid of the parent folder where the page and pageFolder will be created
     * @return FirstSpirit pageRef or null.
     * @deprecated Use {@link #createPage(String, String, String, boolean)} with the additional Parameter.
     */
    PageRef createPage(final String name, final String pageTemplateUid, final String targetPageFolder);

    /**
     * Creates a page in the given targetPageFolder.
     * Also creates a pageRef with the same structure.
     *
     * @param name             Uid of the page and pageFolder to be created
     * @param pageTemplateUid  Page template to be used for creation
     * @param targetPageFolder Uid of the parent folder where the page and pageFolder will be created
     * @param createFolders    Create pageFolder and pageRefFolder as parent of the page
     * @return FirstSpirit pageRef or null.
     */
    PageRef createPage(final String name, final String pageTemplateUid, final String targetPageFolder, final boolean createFolders);
    
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
     *
     * @param deployment
     * @return FirstSpirit scheduleEntryState.
     */
    ScheduleEntryState deploy(final String deployment);

    /**
     * Returns a project specific broker.
     *
     * @return specialist broker.
     */
    SpecialistsBroker getProjectBroker();
}
