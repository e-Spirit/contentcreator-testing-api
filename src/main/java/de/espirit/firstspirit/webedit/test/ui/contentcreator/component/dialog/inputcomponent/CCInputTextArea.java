package de.espirit.firstspirit.webedit.test.ui.contentcreator.component.dialog.inputcomponent;

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
     * @param text
     */
    void setText(String text);
}
