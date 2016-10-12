package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

/**
 * Provides access to an input text component
 */
public interface CCInputText extends CCInputComponent{
    /**
     * Returns the text within the input text component
     * @return text
     */
    String text();

    /**
     * Sets the text of the input text component
     * @param text text to set
     */
    void setText(String text);
}
