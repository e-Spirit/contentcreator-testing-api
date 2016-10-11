package de.espirit.firstspirit.webedit.test.ui.firstspirit;

import de.espirit.firstspirit.access.store.sitestore.PageRef;
import de.espirit.firstspirit.io.ServerConnection;

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
}
