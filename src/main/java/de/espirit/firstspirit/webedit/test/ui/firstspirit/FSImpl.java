package de.espirit.firstspirit.webedit.test.ui.firstspirit;

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
import org.apache.log4j.Logger;

/**
 * Implementation of the {@link FS FirstSpirit server adapter}.
 */
public class FSImpl implements FS {
    private static final Logger LOGGER = Logger.getLogger(FSImpl.class);

    private final ServerConnection connection;
    private String projectName;

    public FSImpl(final ServerConnection connection, String projectName) {
        this.connection = connection;
        this.projectName = projectName;
    }

    @Override
    public ServerConnection connection() {
        return connection;
    }

    @Override
    public PageRef createPage(String name, String pageTemplateUid, String targetPageFolder) {
        SpecialistsBroker projectSpecialistBroker = connection.getBroker().requireSpecialist(BrokerAgent.TYPE).getBrokerByProjectName(projectName);
        StoreElementAgent storeElementAgent = projectSpecialistBroker.requireSpecialist(StoreElementAgent.TYPE);
        PageFolder existingPageFolder = (PageFolder) storeElementAgent.loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false);

        PageTemplate pageTemplate = (PageTemplate) storeElementAgent.loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false);

        if (existingPageFolder != null && pageTemplate != null)
        {
            try {
                PageFolder pageFolder = existingPageFolder.createPageFolder(name);
                Page page = pageFolder.createPage(name, pageTemplate, true);

                SiteStoreFolder existingSiteStoreFolder = (SiteStoreFolder) storeElementAgent.loadStoreElement(targetPageFolder, SiteStoreFolder.UID_TYPE, false);

                if (existingSiteStoreFolder != null) {
                    PageRefFolder pageRefFolder = existingSiteStoreFolder.createPageRefFolder(name);
                    return pageRefFolder.createPageRef(name, page, true);
                }

            } catch (ElementDeletedException | LockException e) {
                LOGGER.error(e);
            }
        }

        return null;
    }
}