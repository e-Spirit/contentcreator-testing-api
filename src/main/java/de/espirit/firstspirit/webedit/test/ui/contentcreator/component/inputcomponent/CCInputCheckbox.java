package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.contentcreator.Web;
import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to an input checkbox component
 */
public interface CCInputCheckbox extends CCInputComponent {

    /**
     * Returns the check box items
     *
     * @return checkbox items
     */
    List<CCInputCheckboxItem> items() throws CCAPIException;

    /**
     * Returns a checkbox item by given displayname
     * @param displayName the displayname of the item
     * @return the item if found, otherwise null
     */
    CCInputCheckboxItem itemByName(@NotNull final String displayName) throws CCAPIException;

    /**
     * Provides access to an input checkbox item
     */
    interface CCInputCheckboxItem extends Web {

        /**
         * Returns the label of the item
         *
         * @return label
         */
        String label() throws CCAPIException;

        /**
         * Returns the checked status of the item
         * @return checked status
         */
        boolean checked();

        /**
         * Toggles the checked status of the item
         */
        void toggle();
    }
}
