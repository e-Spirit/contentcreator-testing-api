package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

/**
 * Provides access to a single menu item of a actions/content menu
 */
public interface MenuItem extends Web {

    /**
     * Perform a click on the menu item
     */
    void click() throws CCAPIException;
}
