package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to an input combobox component
 */
public interface CCInputComboBox extends CCInputComponent{
    /**
     * Returns the combobox items
     *
     * @return combobox items
     */
    List<CCInputComboBoxItem> items();

    /**
     * Returns a combobox item by given displayname
     * @param displayName the displayname of the item
     * @return the item if found, otherwise null
     */
    CCInputComboBoxItem itemByName(@NotNull final String displayName);

    /**
     * Returns the currently selected item
     *
     * @return currently selected item
     */
    CCInputComboBoxItem selectedItem();

    /**
     * Provides access to a combobox item
     */
    interface CCInputComboBoxItem
    {
        /**
         * Returns the label of the item
         * @return Label of the item
         */
        String label();

        /**
         * Select the item
         */
        void select();
    }
}
