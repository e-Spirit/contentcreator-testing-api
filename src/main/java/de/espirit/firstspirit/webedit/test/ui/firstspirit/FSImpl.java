package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import de.espirit.firstspirit.access.AdminService;
import de.espirit.firstspirit.access.schedule.ScheduleEntry;
import de.espirit.firstspirit.access.schedule.ScheduleEntryControl;
import de.espirit.firstspirit.access.schedule.ScheduleEntryRunningException;
import de.espirit.firstspirit.access.schedule.ScheduleEntryState;
import de.espirit.firstspirit.access.schedule.ScheduleStorage;
import de.espirit.firstspirit.access.store.ElementDeletedException;
import de.espirit.firstspirit.access.store.LockException;
import de.espirit.firstspirit.access.store.pagestore.Page;
import de.espirit.firstspirit.access.store.pagestore.PageFolder;
import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.access.store.sitestore.PageRefFolder;
import de.espirit.firstspirit.access.store.sitestore.SiteStoreFolder;
import de.espirit.firstspirit.access.store.templatestore.PageTemplate;
import de.espirit.firstspirit.agency.BrokerAgent;
import de.espirit.firstspirit.agency.SpecialistsBroker;
import de.espirit.firstspirit.agency.StoreElementAgent;
import de.espirit.firstspirit.io.ServerConnection;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.component.FSProject;
import de.espirit.firstspirit.webedit.test.ui.firstspirit.component.FSProjectImpl;

/**
 * Implementation of the {@link FS FirstSpirit server adapter}.
 */
public class FSImpl implements FS {

    private static final Logger LOGGER = Logger.getLogger(FSImpl.class);

    private final ServerConnection connection;
    private final String projectName;

    public FSImpl(final ServerConnection connection, final String projectName) {
        this.connection = connection;
        this.projectName = projectName;
    }

    @Override
    public ServerConnection connection() {
        return this.connection;
    }

    @Deprecated
    @Override
    public PageRef createPage(final String name, final String pageTemplateUid, final String targetPageFolder) {
        // @ToDo: Method is deprecated.
        return this.createPage(name, pageTemplateUid, targetPageFolder, true);
    }

    @Override
    public PageRef createPage(String name, String pageTemplateUid, String targetPageFolder, boolean createFolders) {
        final SpecialistsBroker projectSpecialistBroker = this.connection
            .getBroker()
            .requireSpecialist(BrokerAgent.TYPE)
            .getBrokerByProjectName(this.projectName);

        final StoreElementAgent storeElementAgent = projectSpecialistBroker
            .requireSpecialist(StoreElementAgent.TYPE);

        final PageFolder existingPageFolder = (PageFolder) storeElementAgent
            .loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false);

        final PageTemplate pageTemplate = (PageTemplate) storeElementAgent
            .loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false);

        if ((existingPageFolder != null) && (pageTemplate != null)) {
            try {
                final PageFolder pageFolder;
                if(createFolders) {
                    pageFolder = existingPageFolder.createPageFolder(name);// @ToDo: 'name' parameter unchecked. Can be null or empty
                } else {
                    pageFolder = existingPageFolder;
                }

                final Page page = pageFolder.createPage(name, pageTemplate, true);

                if(createFolders) {
                    final SiteStoreFolder existingSiteStoreFolder = (SiteStoreFolder) storeElementAgent
                        .loadStoreElement(targetPageFolder, SiteStoreFolder.UID_TYPE, false);

                    if (existingSiteStoreFolder != null) {
                        final PageRefFolder pageRefFolder = existingSiteStoreFolder.createPageRefFolder(name);
                        return pageRefFolder.createPageRef(name, page, true);
                    }
                } else {
                    final PageRefFolder existingPageRefFolder = (PageRefFolder) storeElementAgent
                        .loadStoreElement(targetPageFolder, PageRefFolder.UID_TYPE, false);
                    if (existingPageRefFolder != null) {
                        return existingPageRefFolder.createPageRef(name, page, true);
                    }
                }
            } catch (ElementDeletedException | LockException e) {
                FSImpl.LOGGER.error(e);
            }
        }

        return null;
    }

    @Override
    public FSProject project() {
        return new FSProjectImpl(this.connection.getProjectByName(this.projectName));
    }

    @Nullable
    @Override
    public ScheduleEntryState deploy(final String scheduleEntryName) {
        final ScheduleStorage scheduleStorage = this.connection.getService(AdminService.class).getScheduleStorage();
        final ScheduleEntry scheduleEntry = scheduleStorage.getScheduleEntry(this.project().get(), scheduleEntryName);

        if (scheduleEntry != null) {
            try {
                final ScheduleEntryControl execute = scheduleEntry.execute();
                execute.awaitTermination();
                return execute.getState();
            } catch (final ScheduleEntryRunningException e) {
                FSImpl.LOGGER.error("", e);
            }

            return null;
        } else {
            throw new IllegalArgumentException("Unknown schedule entry '" + scheduleEntryName + "'.");
        }
    }
}
