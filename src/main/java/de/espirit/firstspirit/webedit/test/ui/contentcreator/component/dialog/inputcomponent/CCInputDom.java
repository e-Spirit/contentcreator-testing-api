package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

/**
 * Provides access to an input dom component
 */
public interface CCInputDom extends CCInputComponent{
    /**
     * Returns the text within the input dom component
     * @return text
     */
    String text();

    /**
     * Clears the text and adds a p-template with the text within the input dom
     * @param text
     */
    void setText(String text);
}
