package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

import de.espirit.firstspirit.webedit.test.ui.exception.CCAPIException;

/**
 * Provides access to an input dom component
 */
public interface CCInputDom extends CCInputComponent{
    /**
     * Returns the text within the input dom component
     * @return text
     */
    @Deprecated
    String text() throws CCAPIException;

    /**
     * Returns the text within the input dom component
     * @return text
     */
    String getText() throws CCAPIException;

    /**
     * Clears the text and adds a p-template with the text within the input dom
     * @param text text to set
     */
    void setText(String text) throws CCAPIException;
}
