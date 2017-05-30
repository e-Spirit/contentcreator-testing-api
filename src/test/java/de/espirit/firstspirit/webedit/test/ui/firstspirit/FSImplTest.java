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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FSImplTest {
    private static final String PROJECT_NAME = "Test Project";
    private ServerConnection _connection;
    private FSImpl _fs;

    @Before
    public void beforeEachTest() {
        _connection = mock(ServerConnection.class, RETURNS_DEEP_STUBS);
        _fs = new FSImpl(_connection, PROJECT_NAME);
    }

    @Test
    public void create_page_with_pagefolder_return_pageref_success() throws LockException, ElementDeletedException {
        // Arrange
        final String name = "testPageName";
        final String targetPageFolder = "testPageFolder";
        final String pageTemplateUid = "testPageTemplate";

        final SpecialistsBroker specialistsBroker = mock(SpecialistsBroker.class);
        final BrokerAgent brokerAgent = mock(BrokerAgent.class);
        final StoreElementAgent storeElementAgent = mock(StoreElementAgent.class);

        final PageFolder existingPageFolder = mock(PageFolder.class);
        final PageTemplate pageTemplate = mock(PageTemplate.class);

        when(_connection
            .getBroker())
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(BrokerAgent.TYPE))
            .thenReturn(brokerAgent);
        when(brokerAgent
            .getBrokerByProjectName(PROJECT_NAME))
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(StoreElementAgent.TYPE))
            .thenReturn(storeElementAgent);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false))
            .thenReturn(existingPageFolder);
        when(storeElementAgent
            .loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false))
            .thenReturn(pageTemplate);

        final PageFolder pageFolder = mock(PageFolder.class);
        final SiteStoreFolder existingSiteStoreFolder = mock(SiteStoreFolder.class);
        final PageRefFolder pageRefFolder = mock(PageRefFolder.class);
        final Page page = mock(Page.class);

        when(existingPageFolder
            .createPageFolder(name))
            .thenReturn(pageFolder);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, SiteStoreFolder.UID_TYPE, false))
            .thenReturn(existingSiteStoreFolder);
        when(existingSiteStoreFolder.createPageRefFolder(name))
            .thenReturn(pageRefFolder);
        when(pageFolder
            .createPage(name, pageTemplate, true))
            .thenReturn(page);
        when(pageRefFolder
            .createPageRef(name, page, true))
            .thenReturn(mock(PageRef.class));

        // Act
        final PageRef pageRef = _fs.createPage(name, pageTemplateUid, targetPageFolder);

        // Assert
        assertNotNull("Page created, expect non-null PageRef.", pageRef);
    }

    @Test
    public void create_page_with_pagefolder_site_store_folder_not_found_return_null_success() throws LockException, ElementDeletedException {
        // Arrange
        final String name = "testPageName";
        final String targetPageFolder = "testPageFolder";
        final String pageTemplateUid = "testPageTemplate";

        final SpecialistsBroker specialistsBroker = mock(SpecialistsBroker.class);
        final BrokerAgent brokerAgent = mock(BrokerAgent.class);
        final StoreElementAgent storeElementAgent = mock(StoreElementAgent.class);

        final PageFolder existingPageFolder = mock(PageFolder.class);
        final PageTemplate pageTemplate = mock(PageTemplate.class);

        when(_connection
            .getBroker())
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(BrokerAgent.TYPE))
            .thenReturn(brokerAgent);
        when(brokerAgent
            .getBrokerByProjectName(PROJECT_NAME))
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(StoreElementAgent.TYPE))
            .thenReturn(storeElementAgent);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false))
            .thenReturn(existingPageFolder);
        when(storeElementAgent
            .loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false))
            .thenReturn(pageTemplate);

        final PageFolder pageFolder = mock(PageFolder.class);

        when(existingPageFolder
            .createPageFolder(name))
            .thenReturn(pageFolder);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, SiteStoreFolder.UID_TYPE, false))
            .thenReturn(null);

        // Act
        final PageRef pageRef = _fs.createPage(name, pageTemplateUid, targetPageFolder);

        // Assert
        assertNull("No page created, expect null.", pageRef);
    }

    @Test
    public void create_page_with_pagefolder_template_not_found_return_null_success() {
        // Arrange
        final String targetPageFolder = "testPageFolder";
        final String pageTemplateUid = "unknownPageTemplate";

        final SpecialistsBroker specialistsBroker = mock(SpecialistsBroker.class);
        final BrokerAgent brokerAgent = mock(BrokerAgent.class);
        final StoreElementAgent storeElementAgent = mock(StoreElementAgent.class);

        when(_connection
            .getBroker())
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(BrokerAgent.TYPE))
            .thenReturn(brokerAgent);
        when(brokerAgent
            .getBrokerByProjectName(PROJECT_NAME))
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(StoreElementAgent.TYPE))
            .thenReturn(storeElementAgent);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false))
            .thenReturn(mock(PageFolder.class));
        when(storeElementAgent
            .loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false))
            .thenReturn(null);

        // Act
        final PageRef pageRef = _fs.createPage("testPageName", pageTemplateUid, targetPageFolder);

        // Assert
        assertNull("No page created, expect null.", pageRef);
    }

    @Test
    public void create_page_with_pagefolder_pagefolder_not_found_return_null_success() {
        // Arrange
        final String targetPageFolder = "unknownPageFolder";
        final String pageTemplateUid = "testPageTemplate";

        final SpecialistsBroker specialistsBroker = mock(SpecialistsBroker.class);
        final BrokerAgent brokerAgent = mock(BrokerAgent.class);
        final StoreElementAgent storeElementAgent = mock(StoreElementAgent.class);

        when(_connection
            .getBroker())
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(BrokerAgent.TYPE))
            .thenReturn(brokerAgent);
        when(brokerAgent
            .getBrokerByProjectName(PROJECT_NAME))
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(StoreElementAgent.TYPE))
            .thenReturn(storeElementAgent);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false))
            .thenReturn(null);
        when(storeElementAgent
            .loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false))
            .thenReturn(mock(PageTemplate.class));

        // Act
        final PageRef pageRef = _fs.createPage("testPageName", pageTemplateUid, targetPageFolder);

        // Assert
        assertNull("No page created, expect null.", pageRef);
    }

    @Test
    public void create_page_without_pagefolder_return_pageref_success() throws LockException, ElementDeletedException {
        // Arrange
        final String name = "testPageName";
        final String targetPageFolder = "testPageFolder";
        final String pageTemplateUid = "testPageTemplate";

        final SpecialistsBroker specialistsBroker = mock(SpecialistsBroker.class);
        final BrokerAgent brokerAgent = mock(BrokerAgent.class);
        final StoreElementAgent storeElementAgent = mock(StoreElementAgent.class);

        final PageFolder existingPageFolder = mock(PageFolder.class);
        final PageTemplate pageTemplate = mock(PageTemplate.class);

        when(_connection
            .getBroker())
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(BrokerAgent.TYPE))
            .thenReturn(brokerAgent);
        when(brokerAgent
            .getBrokerByProjectName(PROJECT_NAME))
            .thenReturn(specialistsBroker);
        when(specialistsBroker
            .requireSpecialist(StoreElementAgent.TYPE))
            .thenReturn(storeElementAgent);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, PageFolder.UID_TYPE, false))
            .thenReturn(existingPageFolder);
        when(storeElementAgent
            .loadStoreElement(pageTemplateUid, PageTemplate.UID_TYPE, false))
            .thenReturn(pageTemplate);

        final PageRefFolder existingPageRefFolder = mock(PageRefFolder.class);
        final Page page = mock(Page.class);

        when(existingPageFolder
            .createPage(name, pageTemplate, true))
            .thenReturn(page);
        when(storeElementAgent
            .loadStoreElement(targetPageFolder, PageRefFolder.UID_TYPE, false))
            .thenReturn(existingPageRefFolder);
        when(existingPageRefFolder
            .createPageRef(name, page, true))
            .thenReturn(mock(PageRef.class));

        // Act
        final PageRef pageRef = _fs.createPage("testPageName", pageTemplateUid, targetPageFolder, false);

        // Assert
        verify(existingPageFolder, never()).createPageFolder(name);
        assertNotNull("Page created, expect non-null PageRef.", pageRef);
    }
}
