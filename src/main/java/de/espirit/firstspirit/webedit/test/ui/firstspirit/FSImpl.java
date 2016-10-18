package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import de.espirit.firstspirit.access.AdminService;
import de.espirit.firstspirit.access.schedule.*;
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
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of the {@link FS FirstSpirit server adapter}.
 */
public class FSImpl implements FS {
    private static final Logger LOGGER = Logger.getLogger(FSImpl.class);

    private final ServerConnection connection;
    private String projectName;

    public FSImpl(final ServerConnection connection, final String projectName) {
        this.connection = connection;
        this.projectName = projectName;
    }

    @Override
    public ServerConnection connection() {
        return connection;
    }

    @Override
    public PageRef createPage(final String name, final String pageTemplateUid, final String targetPageFolder) {
        final SpecialistsBroker projectSpecialistBroker = connection.getBroker().requireSpecialist(BrokerAgent.TYPE).getBrokerByProjectName(projectName);
        final StoreElementAgent storeElementAgent = projectSpecialistBroker.requireSpecialist(StoreElementAgent.TYPE);
        final PageFolder existingPageFolder = (PageFolder) storeElementAgent.loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false);

        final PageTemplate pageTemplate = (PageTemplate) storeElementAgent.loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false);

        if (existingPageFolder != null && pageTemplate != null)
        {
            try {
                final PageFolder pageFolder = existingPageFolder.createPageFolder(name);
                final Page page = pageFolder.createPage(name, pageTemplate, true);

                final SiteStoreFolder existingSiteStoreFolder = (SiteStoreFolder) storeElementAgent.loadStoreElement(targetPageFolder, SiteStoreFolder.UID_TYPE, false);

                if (existingSiteStoreFolder != null) {
                    final PageRefFolder pageRefFolder = existingSiteStoreFolder.createPageRefFolder(name);
                    return pageRefFolder.createPageRef(name, page, true);
                }

            } catch (ElementDeletedException | LockException e) {
                LOGGER.error(e);
            }
        }

        return null;
    }

    @Override
    public FSProject project() {
        return new FSProjectImpl(connection.getProjectByName(projectName));
    }

    @Nullable
    @Override
    public ScheduleEntryState deploy(final String scheduleEntryName) {
        final ScheduleStorage scheduleStorage = connection.getService(AdminService.class).getScheduleStorage();
        final ScheduleEntry scheduleEntry = scheduleStorage.getScheduleEntry(project().get(), scheduleEntryName);
        try {
            final ScheduleEntryControl execute = scheduleEntry.execute();
            execute.awaitTermination();
            return execute.getState();
        } catch (final ScheduleEntryRunningException e) {
            LOGGER.error("", e);
        }
        return null;
    }
}
