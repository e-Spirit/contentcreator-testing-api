package de.espirit.firstspirit.webedit.test.ui.webedit.component.menu;

import de.espirit.firstspirit.webedit.test.ui.webedit.component.Web;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    MenuItem menuItem(@NotNull final String displayName);
}
