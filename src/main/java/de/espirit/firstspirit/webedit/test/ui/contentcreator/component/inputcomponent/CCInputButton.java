package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

/**
 * Provides access to an input button component
 */
public interface CCInputButton extends CCInputComponent{
    /**
     * Returns the label from the input button
     * @return label
     */
    String label() throws CCAPIException;

    /**
     * Clicks the input button
     */
    void click();

    /**
     * Returns the status of the button
     * @return true if enabled, false if disables
     */
    boolean enabled();
}
