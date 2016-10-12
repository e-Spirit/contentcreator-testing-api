package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.menu;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Provides access to a specific WebEdit menu (action menu, content menu)
 */
public interface Menu extends Web {

    /**
     * Gets the menu item with the given display name
     *
     * @param displayName The display name of the item
     * @return Returns the menu item if found
     */
    MenuItem menuItem(@NotNull final String displayName) throws CCAPIException;


    /**
     * Gets all menu items
     *
     * @return Returns the menu items if found
     */
    List<MenuItem> menuItems() throws CCAPIException;

    /**
     * Opens the menu (hovers over the menu)
     */
    WebElement open() throws CCAPIException;
}
