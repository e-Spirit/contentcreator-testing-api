package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.inputcomponent;

/**
 * Provides access to an input text area component
 */
public interface CCInputTextArea extends CCInputComponent{
    /**
     * Returns the text within the input text area component
     * @return text
     */
    String text();

    /**
     * Sets the text of the input text area component
     * @param text text to set
     */
    void setText(String text);
}
