package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to an input radio component
 */
public interface CCInputRadio extends CCInputComponent {
    /**
     * Returns the radio items
     *
     * @return radio items
     */
    List<CCInputRadioItem> items();

    /**
     * Returns a radio item by given displayname
     * @param displayName the displayname of the item
     * @return the item if found, otherwise null
     */
    CCInputRadioItem itemByName(@NotNull final String displayName);

    /**
     * Returns the currently selected item
     *
     * @return currently selected item
     */
    CCInputRadioItem selectedItem();

    /**
     * Provides access to an input radio item
     */
    interface CCInputRadioItem extends Web{

        /**
         * Returns the label of the item
         *
         * @return label
         */
        String label();

        /**
         * Returns the checked status of the item
         * @return checked status
         */
        boolean checked();

        /**
         * Selects the item
         */
        void select();
    }
}
